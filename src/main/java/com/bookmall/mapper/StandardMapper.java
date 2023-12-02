package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookmall.domain.entity.Standard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
@Mapper
public interface StandardMapper extends BaseMapper<Standard> {
    @Update("update book_standard set store = #{num} where book_id = #{book_id} and value = #{standard}")
    void deductStore(@Param("book_id") long book_id, @Param("standard") String standard, @Param("num") int num);

    @Select("select store from book_standard where book_id = #{book_id} and value = #{standard}")
    int getStore(@Param("book_id") long book_id, @Param("standard")String standard);
}
