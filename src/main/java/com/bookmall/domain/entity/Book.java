package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 书籍实体类
 * @author Rayce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("book")
public class Book extends Model<Book> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 商品名称 
      */
    private String name;

    /**
      * 商品描述 
      */
    private String description;



    /**
      * 折扣 
      */
    private Double discount;


    /**
      * 销量 
      */
    private Integer sales;

    /*
    *销售额
    */
    private BigDecimal saleMoney;

    /**
      * 分类id 
      */
    private Long categoryId;

    /**
      * 商品图片 
      */
    private String imgs;
    /**
      * 创建时间 
      */
    private String createTime;
    /**
      * 是否推荐：0不推荐，1推荐 
      */
    private Boolean recommend;
    /**
     * 是否删除
     */
    private Boolean isDelete;
    /**
     * 原价
     */
    @TableField(exist = false)
    private BigDecimal price;
}