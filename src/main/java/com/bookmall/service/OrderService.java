package com.bookmall.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.config.TokenUtils;
import com.bookmall.constants.Constants;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.Order;
import com.bookmall.domain.entity.OrderBooks;
import com.bookmall.domain.entity.OrderItem;
import com.bookmall.exception.ServiceException;
import com.bookmall.mapper.BookMapper;
import com.bookmall.mapper.OrderBooksMapper;
import com.bookmall.mapper.OrderMapper;
import com.bookmall.mapper.StandardMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.bookmall.constants.RedisConstants.BOOK_TOKEN_KEY;

/**
 * 订单请求逻辑层实现
 * @author Rayce
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderBooksMapper orderBooksMapper;
    @Resource
    private StandardMapper standardMapper;
    @Resource
    private BookMapper bookMapper;
    @Resource
    private CartService cartService;
    @Resource
    private RedisTemplate<String, Book> redisTemplate;

    /**
     * 新增订单保存
     * @param order
     * @return
     */
    @Transactional
    public String saveOrder(Order order) {
        order.setUserId(TokenUtils.getCurrentUser().getId());
        String orderNo = DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(6);
        order.setOrderNo(orderNo);
        order.setCreateTime(DateUtil.now());
        orderMapper.insert(order);

        OrderBooks orderBooks = new OrderBooks();
        orderBooks.setOrderId(order.getId());
        //遍历order里携带的books数组，并用orderItem对象来接收
        String books = order.getBooks();
        List<OrderItem> orderItems = JSON.parseArray(books, OrderItem.class);
        for (OrderItem orderItem : orderItems) {
            long book_id = orderItem.getId();
            String standard = orderItem.getStandard();
            int num = orderItem.getNum();
            orderBooks.setBookId(book_id);
            orderBooks.setCount(num);
            orderBooks.setStandard(standard);
            //插入到order_book表
            orderBooksMapper.insert(orderBooks);
        }
        // 清除购物车
        cartService.removeById(order.getCartId());
        return orderNo;
    }

    /**
     * 给订单付款
     * @param orderNo
     */
    @Transactional
    public void payOrder(String orderNo) {
        //更改状态为代付款
        orderMapper.payOrder(orderNo);
        //给对应规格减库存
        Map<String, Object> orderMap = orderMapper.selectByOrderNo(orderNo);
        int count = (int) orderMap.get("count");
        Object bookIdObj = orderMap.get("bookId");
        Long bookId = null;
        if(bookIdObj instanceof Long) {
            bookId = (Long) bookIdObj;
        } else if(bookIdObj != null) {
            try {
                bookId = Long.parseLong(bookIdObj.toString());
            } catch (NumberFormatException e) {
                throw new ServiceException(Constants.CODE_500, "商品ID不正确");
            }
        }

        if(bookId == null) {
            throw new ServiceException(Constants.CODE_500, "商品ID不存在");
        }
        String standard = (String) orderMap.get("standard");
        int store = standardMapper.getStore(bookId, standard);
        if (store < count) {
            throw new ServiceException(Constants.CODE_500, "库存不足");
        }
        standardMapper.deductStore(bookId, standard, store - count);

        //给对应商品加销量和销售额
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getOrderNo, orderNo);
        Order one = getOne(orderLambdaQueryWrapper);
        BigDecimal totalPrice = one.getTotalPrice();
        bookMapper.saleBook(bookId, count, totalPrice);

        // redis 增销量
        String redisKey = BOOK_TOKEN_KEY + bookId;
        ValueOperations<String, Book> valueOperations = redisTemplate.opsForValue();
        Book book = valueOperations.get(redisKey);
        if(!ObjectUtils.isEmpty(book)) {
            book.setSales(book.getSales() + count);
            valueOperations.set(redisKey, book);
        }
    }

    public List<Map<String, Object>> selectByUserId(int userId) {
        return orderMapper.selectByUserId(userId);
    }

    public boolean receiveOrder(String orderNo) {
        return orderMapper.receiveOrder(orderNo);
    }

    public Map<String, Object> selectByOrderNo(String orderNo) {
        return orderMapper.selectByOrderNo(orderNo);
    }

    public void delivery(String orderNo) {
        LambdaUpdateWrapper<Order> orderLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        orderLambdaUpdateWrapper.eq(Order::getOrderNo, orderNo)
                .set(Order::getState, "已发货");
        update(orderLambdaUpdateWrapper);
    }
}
