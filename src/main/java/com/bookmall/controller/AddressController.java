package com.bookmall.controller;

import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.constants.Constants;
import com.bookmall.domain.entity.Address;
import com.bookmall.service.AddressService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地址请求Controller层
 *
 */
@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/address")
public class AddressController {
    // 需要对请求做拦截， 如果没有登录的用户不能够访问
    @Resource
    private AddressService addressService;

    // task1 获取所有地址
    @GetMapping
    public Result getAll(){
        List<Address> list = addressService.list();
        return Result.success(list);
    }
    // task2 查询对应id的地址
    @GetMapping("/{userId}")
    public Result findAllById(@PathVariable Long userId) {
        return Result.success(addressService.findAllById(userId));
    }

    // task3 新增地址
    @PostMapping
    public Result save(@RequestBody Address address) {
        boolean b = addressService.saveOrUpdate(address);
        if(b){
            return Result.success();
        }else{
            return Result.error(Constants.CODE_500,"保存地址失败");
        }

    }

    // task4 删除地址
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        addressService.removeById(id);
        return Result.success();
    }
    // task5 更新地址
    @PutMapping
    public Result update(@RequestBody Address address) {
        addressService.updateById(address);
        return Result.success();
    }

}
