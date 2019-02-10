package com.cn.lx.entity;

import com.cn.lx.enums.OrderStatusEnums;
import com.cn.lx.enums.PayStatusEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "order_master")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class OrderMaster {

    @Id
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

    /**订单状态，默认为0新下单*/
    private Integer orderStatus = OrderStatusEnums.NEW.getCode();

    /**支付状态*，默认为0未支付*/
    private Integer payStatus = PayStatusEnums.WAIT.getCode();

    /**创建时间*/
    private Date createTime;

    /**更新时间*/
    private Date updateTime;
}
