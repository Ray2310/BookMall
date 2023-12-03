package com.bookmall.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.Book;
import com.bookmall.domain.entity.Carousel;
import com.bookmall.domain.entity.User;
import com.bookmall.service.BookService;
import com.bookmall.service.CarouselService;
import com.bookmall.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/carousel")
public class CarouselController {
    @Resource
    private CarouselService carouselService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserService userService;
    @Resource
    private BookService bookService;

    public User getUser() {
        String token = request.getHeader("token");
        String username = JWT.decode(token).getAudience().get(0);
        return userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    /*
    查询
    */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(carouselService.getById(id));
    }

    @GetMapping
    public Result findAll() {
        List<Carousel> list = carouselService.getAllCarousel();
        return Result.success(list);
    }

    /*
    保存
    */
    @Authority(AuthorityType.requireAuthority)
    @PostMapping
    public Result save(@RequestBody Carousel carousel) {
        Book book = bookService.getById(carousel.getBookId());
        if(book == null) {
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getBookId());
        }
        carouselService.saveOrUpdate(carousel);
        return Result.success();
    }
    @Authority(AuthorityType.requireAuthority)
    @PutMapping
    public Result update(@RequestBody Carousel carousel) {
        Book book = bookService.getById(carousel.getBookId());
        if(book == null) {
            return Result.error("400", "商品id错误，未查询到商品id = " + carousel.getBookId());
        }
        carouselService.updateById(carousel);
        return Result.success();
    }

    /*
    删除
    */
    @Authority(AuthorityType.requireAuthority)
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        carouselService.removeById(id);
        return Result.success();
    }





}
