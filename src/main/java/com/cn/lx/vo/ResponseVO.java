package com.cn.lx.vo;

import lombok.Data;

import java.awt.print.Pageable;
import java.io.Serializable;

@Data
public class ResponseVO<T> implements Serializable {

    /** 状态码 0 表示成功 */
    private Integer code;

    /** 状态码说明 */
    private String msg;

    /** 返回数据*/
    private T data;

}
