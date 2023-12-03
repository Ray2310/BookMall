package com.bookmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.commonUtils.BaseApi;
import com.bookmall.domain.entity.Carousel;
import com.bookmall.domain.entity.Category;
import com.bookmall.domain.entity.IconCategory;
import com.bookmall.mapper.CarouselMapper;
import com.bookmall.mapper.CategoryMapper;
import com.bookmall.mapper.IconCategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 轮波图请求逻辑层实现
 * @author Rayce
 */
@Service
public class CarouselService extends ServiceImpl<CarouselMapper, Carousel> {

    @Resource
    private CarouselMapper carouselMapper;

    public List<Carousel> getAllCarousel() {
        return carouselMapper.getAllCarousel();
    }
}
