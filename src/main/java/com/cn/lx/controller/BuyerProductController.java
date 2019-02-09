package com.cn.lx.controller;

import com.cn.lx.vo.ProductInfoResponseVO;
import com.cn.lx.vo.ProductResponseVO;
import com.cn.lx.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/sell/buyer/product")
public class BuyerProductController {

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseVO test(){
        ResponseVO responseVO = new ResponseVO();
        ProductResponseVO productResponseVO = new ProductResponseVO();
        ProductInfoResponseVO productInfoResponseVO = new ProductInfoResponseVO();

        productResponseVO.setProductInfos(Arrays.asList(productInfoResponseVO));
        responseVO.setData(Arrays.asList(productResponseVO));

        responseVO.setCode(0);
        responseVO.setMsg("成功");

        return responseVO;
    }


}
