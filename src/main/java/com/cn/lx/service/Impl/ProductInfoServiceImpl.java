package com.cn.lx.service.Impl;

import com.cn.lx.dao.ProductInfoRepository;
import com.cn.lx.entity.ProductInfo;
import com.cn.lx.enums.ProductStatusEnums;
import com.cn.lx.enums.ResultEnum;
import com.cn.lx.exceptions.SellException;
import com.cn.lx.service.ProductInfoService;
import com.cn.lx.vo.CarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void increaseStock(List<CarVO> carVOS) {
        for(CarVO carVO : carVOS){
            ProductInfo productInfo = productInfoRepository.queryByProductId(carVO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + carVO.getProductQuantity();

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }


    @Override
    @Transactional
    public void decreaseStock(List<CarVO> carVOS) {
        for(CarVO carVO : carVOS){
            ProductInfo productInfo = productInfoRepository.queryByProductId(carVO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - carVO.getProductQuantity();
            if(result <0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
