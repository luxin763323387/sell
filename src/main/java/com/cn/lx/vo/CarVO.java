package com.cn.lx.vo;

import lombok.Data;

@Data
public class CarVO {


    /** 商品Id. */
    private String productId;

    /** 数量. */
    private Integer productQuantity;

    public CarVO(String productId, Integer productQuantity){
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
