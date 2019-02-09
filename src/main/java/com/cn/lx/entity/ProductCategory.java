package com.cn.lx.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ToString
@Table(name = "product_category")
//动态更新
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**类目id**/
    private Integer categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 类目类别
     */
    private Integer categoryType;

    /**创建时间**/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间**/
    //@JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    public ProductCategory(String categoryName, Integer categoryType){
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
