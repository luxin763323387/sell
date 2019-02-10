package com.cn.lx.service;

import com.cn.lx.entity.OrderMaster;
import com.cn.lx.vo.OrderMasterVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderMasterService {

    /**创建订单*/
    OrderMasterVO create(OrderMasterVO orderMasterVO);

    /** 查询单个订单*/
    OrderMasterVO findOne(String orderId);

    /** 查询订单列表*/
    Page<OrderMaster> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单*/
    OrderMasterVO cancel(OrderMasterVO orderMasterVO);

    /** 完结订单*/
    OrderMasterVO finish(OrderMasterVO orderMasterVO);

    /** 支付订单*/
    OrderMasterVO paid(OrderMasterVO orderMasterVO);

}
