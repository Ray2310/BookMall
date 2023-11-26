package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.OrderBooks;
import com.bookmall.mapper.OrderBooksMapper;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.stereotype.Service;
/**
 * 书籍订单请求逻辑层实现
 * @author Rayce
 */
@Service
public class OrderBooksService extends ServiceImpl<OrderBooksMapper, OrderBooks> {
}
