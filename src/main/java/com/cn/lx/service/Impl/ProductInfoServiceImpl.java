package com.cn.lx.service.Impl;

import com.cn.lx.dao.ProductInfoRepository;
import com.cn.lx.entity.ProductInfo;
import com.cn.lx.enums.ProductStatusEnums;
import com.cn.lx.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findById(String productId) {
        return productInfoRepository.queryByProductId(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        List<ProductInfo> productInfos = productInfoRepository.queryByProductStatus(ProductStatusEnums.UP.getCode());
        return productInfos;
    }

    @Override
    public Page<ProductInfo> findByPage(Pageable pageable) {
        Page<ProductInfo> productInfoPage = productInfoRepository.findAll(pageable);
        return productInfoPage;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
         ProductInfo productInfo1 = productInfoRepository.save(productInfo);
         productInfo1.setCreateTime(new Date());
         productInfo1.setUpdateTime(new Date());
        return productInfo1;
    }
}
