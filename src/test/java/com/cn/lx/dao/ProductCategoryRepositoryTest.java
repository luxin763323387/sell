package com.cn.lx.dao;

import com.cn.lx.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        System.out.println(productCategoryRepository.queryByCategoryId(1));
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = new  ProductCategory();
        productCategory.setCategoryName("添加测试");
        productCategory.setCategoryType(3);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = productCategoryRepository.queryByCategoryId(2);
        productCategory.setCategoryType(10);
        productCategory.setUpdateTime(new Date());
        productCategoryRepository.save(productCategory);
    }

    @Test
    @Transactional
    public void findListTest(){
        List<Integer>list = Arrays.asList(2,3,4);
        List<ProductCategory> productCategories = productCategoryRepository.queryByCategoryTypeIn(list);
        Assert.assertNotEquals(0,productCategories.size());
    }
}