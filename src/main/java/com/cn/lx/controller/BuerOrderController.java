package com.cn.lx.controller;

import com.cn.lx.converter.OrderForm2OrderMasterVO;
import com.cn.lx.enums.ResultEnum;
import com.cn.lx.exceptions.SellException;
import com.cn.lx.from.OrderForm;
import com.cn.lx.service.OrderMasterService;
import com.cn.lx.vo.OrderMasterVO;
import com.cn.lx.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/buyer/order")
public class BuerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    //创建订单
    @PostMapping(value = "/create")
    public ResponseVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单]参数不正确", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        OrderMasterVO orderMasterVO = OrderForm2OrderMasterVO.convert(orderForm);
        if (CollectionUtils.isEmpty(orderMasterVO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderMasterVO createResult = orderMasterService.create(orderMasterVO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResponseVO.success(map);
    }

    //订单列表
    @GetMapping(value = "/list")
    public ResponseVO<List<OrderMasterVO>> list(
            @RequestParam(name = "openid") String openid,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("[订单列表]openid不正确");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderMasterVO> orderMasterVOPage = orderMasterService.findList(openid, pageRequest);
        return ResponseVO.success(orderMasterVOPage.getContent());
    }

    //订单详情
    @GetMapping(value = "/detail")
    public ResponseVO<OrderMasterVO> detail(
            @RequestParam(name = "openid") String openid,
            @RequestParam(name = "orderId") String orderId) {
        OrderMasterVO orderMasterVO = orderMasterService.findOne(orderId);
        return ResponseVO.success(orderMasterVO);
    }

    //取消订单
    @GetMapping(value = "/cancel")
    public ResponseVO cancel(
            @RequestParam(name = "openid") String openid,
            @RequestParam(name = "orderId") String orderId) {
        OrderMasterVO orderMasterVO = orderMasterService.findOne(orderId);
        return ResponseVO.success(orderMasterVO);
    }
}
