package com.bookmall.controller;

import com.bookmall.commonUtils.Result;
import com.bookmall.service.StandardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 规格实体类
 *   关联图书规格， 具体实现内容在BookController
 */
@RestController
public class StandardController {
    @Resource
    private StandardService standardService;
    // task 获取商品的规格信息


}
