package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 图书规格实体类
 * @author Rayce
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("book_standard")
public class Standard extends Model<Standard> {

    /**
      * 商品id 
      */
    private Integer bookId;

    /**
      * 商品规格 
      */
    private String value;

    /**
      * 该规格的价格 
      */
    private BigDecimal price;

    /**
      * 该规格的库存 
      */
    private Integer store;

}