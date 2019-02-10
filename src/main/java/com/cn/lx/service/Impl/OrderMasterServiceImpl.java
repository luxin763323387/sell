package com.cn.lx.service.Impl;

import com.cn.lx.dao.OrderDetailRepository;
import com.cn.lx.dao.OrderMasterRepository;
import com.cn.lx.dao.ProductInfoRepository;
import com.cn.lx.entity.OrderDetail;
import com.cn.lx.entity.OrderMaster;
import com.cn.lx.entity.ProductInfo;
import com.cn.lx.entity.ResultEnum;
import com.cn.lx.exceptions.SellException;
import com.cn.lx.service.OrderMasterService;
import com.cn.lx.service.ProductInfoService;
import com.cn.lx.vo.OrderMasterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderMasterVO create(OrderMasterVO orderMasterVO) {

        BigDecimal orderAmount = new BigDecimal(0);

        //1 查询商品(数量，价格)
        List<OrderDetail> orderDetailList = orderMasterVO.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            ProductInfo productInfo = productInfoService.findById(orderDetail.getOrderId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            
            //计算总价
            orderAmount = orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
        }
        
        //写入订单数据库

        //扣库存
        return null;
    }

    @Override
    public OrderMasterVO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderMaster> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderMasterVO cancel(OrderMasterVO orderMasterVO) {
        return null;
    }

    @Override
    public OrderMasterVO finish(OrderMasterVO orderMasterVO) {
        return null;
    }

    @Override
    public OrderMasterVO paid(OrderMasterVO orderMasterVO) {
        return null;
    }
}
