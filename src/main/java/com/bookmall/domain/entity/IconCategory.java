package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分类图标实体类
 * @author Rayce
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@TableName(value="icon_category")
public class IconCategory {
    private Long iconId;
    private Long categoryId;

}
