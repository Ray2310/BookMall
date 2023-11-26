package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("address")
public class Address extends Model<Address> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 联系人 
      */
    private String linkUser;

    /**
      * 联系地址 
      */
    private String linkAddress;

    /**
      * 联系电话 
      */
    private String linkPhone;

    /**
      * 所属用户 
      */
    private Long userId;

}