package com.cn.lx.controller;

import com.cn.lx.entity.ProductCategory;
import com.cn.lx.entity.ProductInfo;
import com.cn.lx.service.ProductCategoryService;
import com.cn.lx.service.ProductInfoService;
import com.cn.lx.vo.ProductInfoResponseVO;
import com.cn.lx.vo.ProductResponseVO;
import com.cn.lx.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseVO list() {
        //1 查询所有商品(上架)
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        //2查询类目
        //List<Integer> categoryTypeList = new ArrayList<>();
     /*   for(ProductInfo productInfo : productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }*/
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());

        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //拼接VO
        //先遍历出category中的 不同类别
        List<ProductResponseVO> productResponseVOS = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductResponseVO productResponseVO = new ProductResponseVO();
            productResponseVO.setCategoryName(productCategory.getCategoryName());
            productResponseVO.setCategoryType(productCategory.getCategoryType());

            //在遍历出category中 info的信息 ->根据categoryTypelist 获取
            List<ProductInfoResponseVO> productInfoResponseVOS = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                // 通过2张表的category_type是否相同 来获取 productInfo
                if (productCategory.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoResponseVO productInfoResponseVO = new ProductInfoResponseVO();
                    productInfoResponseVO.setProductId(productInfo.getProductId());
                    productInfoResponseVO.setProductName(productInfo.getProductName());
                    //productInfoResponseVO.setCategoryType(productCategory.getCategoryType());
                    productInfoResponseVO.setProductPrice(productInfo.getProductPrice());
                    productInfoResponseVO.setProductDescription(productInfo.getProductDescription());
                    productInfoResponseVO.setProductIcon(productInfo.getProductIcon());
                    productInfoResponseVOS.add(productInfoResponseVO);
                }
            }
            productResponseVO.setProductInfos(productInfoResponseVOS);
            productResponseVOS.add(productResponseVO);
        }
        return ResponseVO.success(productResponseVOS);
    }
}
