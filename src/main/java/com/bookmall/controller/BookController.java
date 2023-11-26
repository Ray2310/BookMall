package com.bookmall.controller;

import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.Address;
import com.bookmall.domain.entity.Book;
import com.bookmall.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Resource
    private BookService bookService;
    //todo task1 获取所有图书信息
    @GetMapping
    public Result getAll(){
        List<Book> list = bookService.list();
        return Result.success(list);
    }
    //todo task2
    //todo task3
    //todo task4
    //todo task5
}
