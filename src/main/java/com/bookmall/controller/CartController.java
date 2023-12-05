package com.bookmall.controller;

import cn.hutool.core.date.DateUtil;
import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.Cart;
import com.bookmall.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车模块请求Controller层
 * 需要登录才能操作， 如果未登录则会直接跳转到登录界面
 */
@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Resource
    private CartService cartService;

    //todo task1 根据购物车id查询
    @GetMapping("/{id}")
    public Result selectById(@PathVariable Long id) {
        return Result.success(cartService.getById(id));
    }

    //todo task2 查找所有用户的购物车
    @GetMapping
    public Result findAll() {
        List<Cart> list = cartService.list();
        return Result.success(list);
    }
    //todo task4 添加购物车
    @PostMapping
    public Result save(@RequestBody Cart cart) {
        cart.setCreateTime(DateUtil.now());

        cartService.saveOrUpdate(cart);
        return Result.success();
    }
    //todo task5 删除购物车
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        cartService.removeById(id);
        return Result.success();
    }

    //todo task6 更新购物车
    @PutMapping
    public Result update(@RequestBody Cart cart) {
        cartService.updateById(cart);
        return Result.success();
    }

    // --------------------后台-----------------------------

    //todo task3 查找某个用户的购物车
    @GetMapping("/userid/{userId}")
    public Result selectByUserId(@PathVariable Long userId) {
        return Result.success(cartService.selectByUserId(userId)) ;
    }
}
