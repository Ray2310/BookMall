package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookmall.domain.entity.Carousel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    @Select("select carousel.*,book.name as book_name,book.imgs as img from carousel,book where book.id = carousel.book_id order by show_order asc")
    List<Carousel> getAllCarousel();
}
