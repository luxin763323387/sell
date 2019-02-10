package com.cn.lx.dao;

import com.cn.lx.entity.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("范先生");
        orderMaster.setBuyerAddress("东风东路699广东港澳中心403");
        orderMaster.setBuyerPhone("15626131368");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(new BigDecimal(100));
        OrderMaster result = orderMasterRepository.save(orderMaster);
        assertEquals("123456",result.getBuyerOpenid());
    }

    @Test
    public void findByBuyerOpenid() throws Exception{
        PageRequest request = new PageRequest(0,2);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid("123456", request);
        System.out.println(result.getTotalElements());
    }
}