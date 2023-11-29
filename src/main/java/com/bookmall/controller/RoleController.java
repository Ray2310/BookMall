package com.bookmall.controller;


import com.bookmall.commonUtils.Result;
import com.bookmall.config.TokenUtils;
import com.bookmall.domain.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class RoleController {

    //todo task1 获取用户权限 前端进行权限校验的
    @PostMapping("/role")
    public Result getUserRole(){
        User currentUser = TokenUtils.getCurrentUser();
        return Result.success(currentUser.getRole());
    }
}
