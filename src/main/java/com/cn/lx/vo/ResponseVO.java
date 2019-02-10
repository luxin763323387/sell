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

    //单例模式
    private ResponseVO(){};

    //业务成功
    public static<M> ResponseVO success(M m){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(0);
        responseVO.setData(m);

        return responseVO;
    }

    //业务失败
    public static ResponseVO serviceFaile(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(1);
        responseVO.setMsg(msg);

        return responseVO;
    }


    //系统异常
    public static ResponseVO appFaile(String msg){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode(999);
        responseVO.setMsg(msg);

        return responseVO;
    }

}
