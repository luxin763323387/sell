package com.cn.lx.service;

import com.cn.lx.entity.ProductInfo;
import com.cn.lx.vo.CarVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findById(String productId);

    //查询所有上架的
    List<ProductInfo> findUpAll();

    //查询所有
    Page<ProductInfo> findByPage(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CarVO> carVOS);

    //减库存
    void decreaseStock(List<CarVO> carVOS);
}
