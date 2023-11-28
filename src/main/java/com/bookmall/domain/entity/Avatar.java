package com.bookmall.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 头像内容实体类
 * @author Rayce
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("avatar")
public class Avatar {
    @TableId(type = IdType.AUTO)
    private int id;
    private String type;
    private long size;
    private String url;
    private String md5;

    public Avatar(String type, long size, String url, String md5) {
        this.type = type;
        this.size = size;
        this.url = url;
        this.md5 = md5;
    }
}
