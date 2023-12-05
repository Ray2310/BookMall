package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName("cart")
public class Cart extends Model<Cart> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 商品数量 
      */
    private Integer count;

    /**
      * 加入时间 
      */
    private String createTime;

    /**
      * 商品id 
      */
    private Long bookId;

    private String standard;

    /**
      * 用户id 
      */
    private Long userId;

}