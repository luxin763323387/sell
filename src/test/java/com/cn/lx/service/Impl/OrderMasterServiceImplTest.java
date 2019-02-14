package com.cn.lx.service.Impl;

import com.cn.lx.entity.OrderDetail;
import com.cn.lx.enums.OrderStatusEnums;
import com.cn.lx.vo.CarVO;
import com.cn.lx.vo.OrderMasterVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    private final String BUYER_OPENID = "1101110";
    private final String ORDER_ID = "1549947490249269229";
    private final String ORDER_ID2 = "1550126529969550485";

    @Test
    public void create() {

        //创建订单
        OrderMasterVO orderMasterVO = new OrderMasterVO();
        orderMasterVO.setBuyerName("廖师兄");
        orderMasterVO.setBuyerAddress("幕课网");
        orderMasterVO.setBuyerPhone("123456789012");
        orderMasterVO.setBuyerOpenid(BUYER_OPENID);

        //创建购物车
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        OrderDetail orderDetail2 = new OrderDetail();

        orderDetail.setProductId("123450");
        orderDetail.setProductQuantity(1);

        //orderDetail2.setProductId("12345");
        //orderDetail2.setProductQuantity(2);

        orderDetails.add(orderDetail);
       // orderDetails.add(orderDetail2);

        orderMasterVO.setOrderDetailList(orderDetails);
        orderMasterService.create(orderMasterVO);
    }

    @Test
    public void findOne() {
        OrderMasterVO result = orderMasterService.findOne(ORDER_ID);
        log.info("查询单个订单",result);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderMasterVO> OrderMasterVOPage = orderMasterService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0, OrderMasterVOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderMasterVO orderMasterVO = orderMasterService.findOne(ORDER_ID2);
        OrderMasterVO result = orderMasterService.cancel(orderMasterVO);
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }
}