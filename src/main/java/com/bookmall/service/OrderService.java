package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Order;
import com.bookmall.mapper.OrderMapper;
import org.springframework.stereotype.Service;
/**
 * 订单请求逻辑层实现
 * @author Rayce
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
}
