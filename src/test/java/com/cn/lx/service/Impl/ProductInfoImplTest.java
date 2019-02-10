package com.cn.lx.service.Impl;

import com.cn.lx.entity.ProductInfo;
import com.cn.lx.enums.ProductStatusEnums;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoImplTest {


    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findById() {
        ProductInfo result = productInfoService.findById("123456");
        Assert.assertEquals(new String("123456"), result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> result = productInfoService.findUpAll();
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void findByPage() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> result = productInfoService.findByPage(request);
        System.out.println(result.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();

        productInfo.setProductId("123450");
        productInfo.setProductName("八期烤鱼");
        productInfo.setProductDescription("超级好吃");
        productInfo.setProductIcon("http://xxxxx.jpg");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setCategoryType(3);
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());

        ProductInfo result = productInfoService.save(productInfo);

        Assert.assertNotNull(result);
    }
}