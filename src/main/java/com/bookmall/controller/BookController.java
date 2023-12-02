package com.bookmall.controller;

import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.constants.Constants;
import com.bookmall.domain.entity.Address;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.Standard;
import com.bookmall.service.BookService;
import com.bookmall.service.StandardService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {


    @Resource
    private StandardService standardService;

    @Resource
    private BookService bookService;
    //todo task1 获取所有图书信息
//    @GetMapping
//    public Result getAll(){
//        List<Book> list = bookService.list();
//        return Result.success(list);
//    }
    //todo task2 前台获取所有的Book 查询推荐图书，即recommend=1
    @GetMapping
    public Result findAll() {
        return Result.success(bookService.findFrontBooks());
    }
    //todo task3 通过id查询图书
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(bookService.getBookById(id));
    }
    //todo task4 获取通过图书id获取图书的规格信息
    @GetMapping("/standard/{id}")
    public Result getStandard(@PathVariable int id) {
        return Result.success(bookService.getStandard(id));
    }
  

    //todo task5

    //------------------------------后台功能-------------------------------------




    //todo task1 保存图书
    @Authority(AuthorityType.requireAuthority)
    @PostMapping
    public Result save(@RequestBody Book book) {
        System.out.println(book);
        return Result.success(bookService.saveOrUpdateBook(book));
    }


    //todo task2 更新图书
    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Book book) {
        bookService.update(book);
        return Result.success();
    }

    //todo task3 删除图书
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success();
    }




    //todo task7 分页获取数据
    @GetMapping("/page")
    public Result findPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @RequestParam(required = false) Integer categoryId) {

        return Result.success(bookService.findPage(pageNum,pageSize,searchText,categoryId));
    }
    @GetMapping("/fullPage")
    public Result findFullPage(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false, defaultValue = "") String searchText,
            @RequestParam(required = false) Integer categoryId) {

        return Result.success(bookService.findFullPage(pageNum,pageSize,searchText,categoryId));
    }


    //todo task8 保存图书规格信息
    @PostMapping("/standard")
    public Result saveStandard(@RequestBody List<Standard> standards, @RequestParam int bookId) {
        //先删除全部旧记录
        standardService.deleteAll(bookId);
        //然后插入新记录
        for (Standard standard : standards) {
            standard.setBookId(bookId);
            if(!standardService.save(standard)){
                return Result.error(Constants.CODE_500,"保存失败");
            }
        }
        return Result.success();
    }

    //todo task9 删除图书的规格信息
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/standard")
    public Result delStandard(@RequestBody Standard standard) {
        boolean delete = standardService.delete(standard);
        if(delete) {
            return Result.success();
        }else {
            return Result.error(Constants.CODE_500,"删除失败");
        }
    }

    //todo task10  修改图书的推荐字段
    //
    @Authority(AuthorityType.requireAuthority)
    @GetMapping("/recommend")
    public Result setRecommend(@RequestParam Long id,@RequestParam Boolean isRecommend){
        return Result.success(bookService.setRecommend(id,isRecommend));
    }


}
