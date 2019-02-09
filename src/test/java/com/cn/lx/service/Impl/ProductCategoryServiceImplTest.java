package com.cn.lx.service.Impl;

import com.cn.lx.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @Test
    public void findById() {
        ProductCategory productCategory = productCategoryService.findById(1);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = productCategoryService.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryType() {
        List<ProductCategory> byCategoryType = productCategoryService.findByCategoryType(Arrays.asList(1, 2, 3, 4));
        Assert.assertNotEquals(0,byCategoryType.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生转向");
        productCategory.setCategoryType(4);
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());
        ProductCategory save = productCategoryService.save(productCategory);
        Assert.assertNotNull(save);
    }
}