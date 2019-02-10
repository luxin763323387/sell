package com.cn.lx.vo;

import com.cn.lx.entity.OrderDetail;
import com.cn.lx.enums.OrderStatusEnums;
import com.cn.lx.enums.PayStatusEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderMasterVO {
    /**订单id*/
    private String orderId;

    /**买家姓名*/
    private String buyerName;

    /**买家手机号码*/
    private String buyerPhone;

    /**买家送货地址*/
    private String buyerAddress;

    /**买家微信openid*/
    private String buyerOpenid;

    /**订单总额*/
    private BigDecimal orderAmount;

    /**订单状态*/
    private Integer orderStatus;

    /**支付状态*/
    private Integer payStatus;

    /**创建时间*/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间*/
   // @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

}
