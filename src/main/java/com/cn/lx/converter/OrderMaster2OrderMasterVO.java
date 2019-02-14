package com.cn.lx.converter;

import com.cn.lx.entity.OrderMaster;
import com.cn.lx.vo.OrderMasterVO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderMasterVO {
    public static OrderMasterVO convert(OrderMaster orderMaster) {

        OrderMasterVO OrderMasterVO = new OrderMasterVO();
        BeanUtils.copyProperties(orderMaster, OrderMasterVO);
        return OrderMasterVO;
    }

    public static List<OrderMasterVO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e -> convert(e)
        ).collect(Collectors.toList());
    }
}
