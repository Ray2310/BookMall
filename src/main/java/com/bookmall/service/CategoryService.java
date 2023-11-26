package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Category;
import com.bookmall.mapper.CategoryMapper;
import org.springframework.stereotype.Service;
/**
 * 分类请求逻辑层实现
 * @author Rayce
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, Category> {
}
