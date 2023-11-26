package com.bookmall.controller;

import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.Address;
import com.bookmall.service.AddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址请求Controller层
 *
 *
 */
//@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/address")
public class AddressController {
    // 需要对请求做拦截， 如果没有登录的用户不能够访问
    @Resource
    private AddressService addressService;

    //todo task1 获取所有地址
    @GetMapping
    public Result getAll(){
        List<Address> list = addressService.list();
        return Result.success(list);
    }
    //todo task2 查询对应id的地址
    //todo task3 新增地址

    //todo task4 删除地址

    //todo task5 更新地址


}
