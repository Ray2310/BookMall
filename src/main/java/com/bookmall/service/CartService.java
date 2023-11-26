package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Cart;
import com.bookmall.mapper.CartMapper;
import org.springframework.stereotype.Service;
/**
 * 购物车请求逻辑层实现
 * @author Rayce
 */
@Service
public class CartService extends ServiceImpl<CartMapper, Cart> {
}
