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

import java.util.List;

/**
 * 图标实体类
 * @author Rayce
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName(value="icon")
public class Icon extends Model<Icon> {
    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 图标的标识码 
      */
    private String value;

    @TableField(exist = false)
    private List<Category> categories;
}