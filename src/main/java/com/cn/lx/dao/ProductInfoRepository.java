package com.cn.lx.dao;

import com.cn.lx.entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    ProductInfo queryByProductId(String productId);

    List<ProductInfo> queryByProductStatus(Integer productStatus);
}
