package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Book;
import com.bookmall.mapper.BookMapper;
import org.springframework.stereotype.Service;
/**
 * 书籍请求逻辑层实现
 * @author Rayce
 */
@Service
public class BookService extends ServiceImpl<BookMapper, Book> {

}
