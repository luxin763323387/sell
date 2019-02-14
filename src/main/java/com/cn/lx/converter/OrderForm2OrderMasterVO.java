package com.cn.lx.converter;

import com.cn.lx.entity.OrderDetail;
import com.cn.lx.enums.ResultEnum;
import com.cn.lx.exceptions.SellException;
import com.cn.lx.from.OrderForm;
import com.cn.lx.vo.OrderMasterVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderMasterVO {
    public static OrderMasterVO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderMasterVO OrderMasterVO = new OrderMasterVO();

        OrderMasterVO.setBuyerName(orderForm.getName());
        OrderMasterVO.setBuyerPhone(orderForm.getPhone());
        OrderMasterVO.setBuyerAddress(orderForm.getAddress());
        OrderMasterVO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("【对象转换】错误, string={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        OrderMasterVO.setOrderDetailList(orderDetailList);

        return OrderMasterVO;
    }
}
