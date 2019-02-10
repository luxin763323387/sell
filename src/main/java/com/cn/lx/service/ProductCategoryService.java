package com.cn.lx.service;

import com.cn.lx.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save (ProductCategory productCategory);
}
