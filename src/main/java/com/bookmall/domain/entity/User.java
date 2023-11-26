package com.bookmall.domain.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户类
 * @author Rayce
 */
@TableName("sys_user")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String address;
    private String avatarUrl;
    private String role;
    @TableField(exist = false)
    private String newPassword;
}
