package com.bookmall.controller;

import com.bookmall.annotation.Authority;
import com.bookmall.annotation.enumUtils.AuthorityType;
import com.bookmall.commonUtils.Result;
import com.bookmall.domain.entity.Category;
import com.bookmall.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;
    //todo task2 查询对应id的分类
    @GetMapping("/{id}")
    public Result findById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }
    //todo task1 查询所有分类
    @GetMapping
    public Result findAll() {
        List<Category> list = categoryService.list();
        return Result.success(list);
    }
    @PostMapping
    public Result save(@RequestBody Category category) {
        categoryService.saveOrUpdate(category);
        return Result.success();
    }
    //todo task3 新增一级分类

    //todo task4 新增下级分类 + 上下级分类关联

    //todo task5 更新分类信息

    //todo task6 删除对应id的分类
    /**
     * 删除分类
     *
     * @param id id
     * @return 结果
     */
  //  @Authority(AuthorityType.requireAuthority)
    //@GetMapping("/delete")
//    public Map<String, Object> delete(@RequestParam("id") Long id) {
//        return categoryService.delete(id);
  //  }



}
