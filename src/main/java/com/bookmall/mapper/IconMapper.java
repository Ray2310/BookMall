package com.bookmall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookmall.domain.entity.Icon;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface IconMapper extends BaseMapper<Icon> {

    @Select("select * from icon")
    List<Icon> getIconCategoryMapList();
}
