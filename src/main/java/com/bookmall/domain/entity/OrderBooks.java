package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 订单图书实体类
 * @author Rayce
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("order_books")
public class OrderBooks extends Model<OrderBooks> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 订单id 
      */
    private Long orderId;

    /**
      * 商品id 
      */
    private Long bookId;

    /**
      * 数量 
      */
    private Integer count;

    /**
      * 商品规格 
      */
    private String standard;


}