package com.cn.lx.dao;

import com.cn.lx.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    ProductCategory queryByCategoryId(Integer id);
    public List<ProductCategory> queryByCategoryTypeIn(List<Integer> categoryTypes);
}
