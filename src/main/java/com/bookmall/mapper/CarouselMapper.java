package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookmall.domain.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    List<Carousel> getAllCarousel();
}
