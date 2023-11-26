package com.bookmall.controller;


import com.bookmall.commonUtils.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    //todo task1 获取用户权限
    @PostMapping("/role")
    public Result getUserRole() {
        // 返回用户权限
        return Result.success();
    }
}
