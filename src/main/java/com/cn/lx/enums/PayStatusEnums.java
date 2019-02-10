package com.cn.lx.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnums {
    WAIT(0 , "未支付"),
    FINISH(1 , "已支付")
    ;

    private Integer code;

    private String msg;

    PayStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
