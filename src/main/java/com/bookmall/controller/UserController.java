package com.bookmall.controller;

import com.bookmall.commonUtils.Result;
import com.bookmall.domain.dto.UserDTO;
import com.bookmall.domain.entity.LoginForm;
import com.bookmall.service.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用户请求Controller层
 *
 *
 */
/*
这个注解表示该控制器下所有接口都可以通过跨域访问，注解内可以指定某一域名
也可以配置config类
 */
//@CrossOrigin
@RestController
public class UserController {
    @Resource
    private UserService userService;


    //todo task1 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm) {
        UserDTO dto = userService.login(loginForm);
        return Result.success(dto);
    }

    //todo task2 用户注册


    //todo task3 获取用户信息

    //todo

    //todo task4 获取用户列表

    //todo task5 根据id对用户进行操作


    //todo task6 重置密码





}
