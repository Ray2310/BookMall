package com.bookmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.constants.Constants;
import com.bookmall.domain.entity.Order;
import com.bookmall.service.OrderService;
import com.sun.xml.internal.fastinfoset.stax.events.Util;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单模块Controller层
 * 需要登录才能操作
 */
@Authority(AuthorityType.requireLogin)
@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Resource
    private OrderService orderService;

    //todo task1 查询某个用户/ 某个订单号的 订单
    @GetMapping("/userid/{userid}")
    public Result selectByUserId(@PathVariable int userid) {
        return Result.success(orderService.selectByUserId(userid));
    }
    @GetMapping("/orderNo/{orderNo}")
    public Result selectByOrderNo(@PathVariable String orderNo) {
        return Result.success(orderService.selectByOrderNo(orderNo));
    }

    //todo task2 获取所有订单
    @GetMapping
    public Result findAll() {
        List<Order> list = orderService.list();
        return Result.success(list);
    }

    //todo task3 分页查询
    @GetMapping("/page")
    public Result findPage(@RequestParam int pageNum,
                           @RequestParam int pageSize,
                           String orderNo,String state){
        IPage<Order> orderPage = new Page<>(pageNum,pageSize);
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.ne("state","待付款");
        if(!Util.isEmptyString(state)){
            orderQueryWrapper.eq("state",state);
        }
        if(!Util.isEmptyString(orderNo)){
            orderQueryWrapper.like("order_no",orderNo);
        }

        orderQueryWrapper.orderByDesc("create_time");
        return Result.success(orderService.page(orderPage,orderQueryWrapper));
    }

    //todo task4 新增订单
    @PostMapping
    public Result save(@RequestBody Order order) {
        String orderNo = orderService.saveOrder(order);
        return Result.success(orderNo);
    }


    //todo task7 订单支付
    @GetMapping("/paid/{orderNo}")
    public Result payOrder(@PathVariable String orderNo){
        orderService.payOrder(orderNo);
        return Result.success();
    }

    //todo task8 发货( 需要管理员权限)
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/delivery/{orderNo}")
    public Result delivery(@PathVariable String orderNo){
        orderService.delivery(orderNo);
        return Result.success();
    }
    //todo task9 确认收货
    @GetMapping("/received/{orderNo}")
    public Result receiveOrder(@PathVariable String orderNo){
        if(orderService.receiveOrder(orderNo)){
            return Result.success();
        }
        else {
            return Result.error(Constants.CODE_500,"确认收货失败");
        }
    }
    //todo task5 修改订单

    /**
     * 更新订单信息
     * @param order
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Order order) {
        orderService.updateById(order);
        return Result.success();
    }

    //todo task6 删除订单
    /**
     * 删除订单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        orderService.removeById(id);
        return Result.success();
    }


}
