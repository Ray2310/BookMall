package com.bookmall.controller;
import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.User;
import com.bookmall.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class testController {

    @Resource
    private UserService userService;
    /**
     * 测试接口
     * @return
     */
    @GetMapping("/test")
    public Result testController(){
        List<User> list = userService.list();
        return Result.success(list);
    }

}
