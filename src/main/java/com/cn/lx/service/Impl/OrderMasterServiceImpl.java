package com.cn.lx.service.Impl;

import com.cn.lx.converter.OrderMaster2OrderMasterVO;
import com.cn.lx.dao.OrderDetailRepository;
import com.cn.lx.dao.OrderMasterRepository;
import com.cn.lx.entity.OrderDetail;
import com.cn.lx.entity.OrderMaster;
import com.cn.lx.entity.ProductInfo;
import com.cn.lx.enums.OrderStatusEnums;
import com.cn.lx.enums.PayStatusEnums;
import com.cn.lx.enums.ResultEnum;
import com.cn.lx.exceptions.SellException;
import com.cn.lx.service.OrderMasterService;
import com.cn.lx.service.ProductInfoService;
import com.cn.lx.utils.KeyUtil;
import com.cn.lx.vo.CarVO;
import com.cn.lx.vo.OrderMasterVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderMasterVO create(OrderMasterVO orderMasterVO) {

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);
        List<CarVO> carVOS = new ArrayList<>();
        //1 查询商品(数量，价格)
        List<OrderDetail> orderDetailList = orderMasterVO.getOrderDetailList();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductIcon(productInfo.getProductIcon());
            orderDetail.setProductId(productInfo.getProductId());
            orderDetail.setProductName(productInfo.getProductName());
            //orderDetail.setProductQuantity(productInfo.getProductStock());  不需要
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetailRepository.save(orderDetail);

            CarVO carVO = new CarVO(orderDetail.getProductId(), orderDetail.getProductQuantity());
            carVOS.add(carVO);
        }

        //3. 写入订单数据库（orderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setBuyerAddress(orderMasterVO.getBuyerAddress());
        orderMaster.setBuyerName(orderMasterVO.getBuyerName());
        orderMaster.setBuyerOpenid(orderMasterVO.getBuyerOpenid());
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        //orderMaster.setCreateTime(orderMaster.getCreateTime());
        orderMaster.setCreateTime(new Date());
        //orderMaster.setUpdateTime(orderMasterVO.getUpdateTime());
        orderMaster.setUpdateTime(new Date());
        orderMaster.setBuyerPhone(orderMasterVO.getBuyerPhone());
        orderMaster.setOrderStatus(orderMaster.getOrderStatus());
        orderMaster.setPayStatus(PayStatusEnums.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //扣库存
        productInfoService.decreaseStock(carVOS);
        orderMasterVO.setOrderId(orderId);
        return orderMasterVO;
    }

    @Override
    public OrderMasterVO findOne(String orderId) {
        //查询订单
        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //查询订单详情
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetails)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderMasterVO orderMasterVO = new OrderMasterVO();
        BeanUtils.copyProperties(orderMaster, orderMasterVO);
        orderMasterVO.setOrderDetailList(orderDetails);
        return orderMasterVO;
    }

    @Override
    public Page<OrderMasterVO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        List<OrderMasterVO> OrderMasterVOList = OrderMaster2OrderMasterVO.convert(orderMasterPage.getContent());

        return new PageImpl<OrderMasterVO>(OrderMasterVOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    public OrderMasterVO cancel(OrderMasterVO orderMasterVO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if (!orderMasterVO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())) {
            log.error("订单状态不正确", orderMasterVO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMasterVO.setOrderStatus(OrderStatusEnums.CANCEL.getCode());
        BeanUtils.copyProperties(orderMasterVO, orderMaster);
        OrderMaster upResult = orderMasterRepository.save(orderMaster);
        if (upResult == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderMasterVO.getOrderDetailList())) {
            log.error("订单中五商品详情", orderMasterVO);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        List<CarVO> carVOList = orderMasterVO.getOrderDetailList().stream()
                .map(e -> new CarVO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(carVOList);

        //如果支付，需要返回退款
        return orderMasterVO;
    }

    @Override
    public OrderMasterVO finish(OrderMasterVO orderMasterVO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderMasterVO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[完结订单] 订单跟新失败",orderMasterVO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderMasterVO.setOrderStatus(OrderStatusEnums.FINISH.getCode());
        BeanUtils.copyProperties(orderMasterVO,orderMaster);
        OrderMaster upResult = orderMasterRepository.save(orderMaster);
        if (upResult == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        return orderMasterVO;
    }

    @Override
    public OrderMasterVO paid(OrderMasterVO orderMasterVO) {
        //判断订单状态
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态
        if(!orderMasterVO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode())){
            log.error("[支付订单] 订单跟新失败",orderMasterVO);
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderMasterVO.getPayStatus().equals(PayStatusEnums.WAIT.getCode())){
            log.error("[支付订单] 订单支付状态失败",orderMasterVO);
        }

        //修改支付状态
        orderMasterVO.setPayStatus (PayStatusEnums.FINISH.getCode());
        BeanUtils.copyProperties(orderMasterVO,orderMaster);
        OrderMaster upResult = orderMasterRepository.save(orderMaster);
        if (upResult == null) {
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        return orderMasterVO;
    }
}
