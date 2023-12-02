package com.bookmall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Standard;
import com.bookmall.mapper.StandardMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 书籍规格请求逻辑层实现
 * @author Rayce
 */
@Service
public class StandardService extends ServiceImpl<StandardMapper, Standard> {
    @Resource
    private StandardMapper standardMapper;

    public boolean delete(Standard standard) {
        QueryWrapper<Standard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id",standard.getBookId());
        queryWrapper.eq("value",standard.getValue());
        return remove(queryWrapper);
    }


    public void deleteAll(int BookId) {
        QueryWrapper<Standard> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("book_id",BookId);
        remove(queryWrapper);
    }
}
