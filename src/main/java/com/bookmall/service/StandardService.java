package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.domain.entity.Standard;
import com.bookmall.mapper.StandardMapper;
import org.springframework.stereotype.Service;
/**
 * 书籍规格请求逻辑层实现
 * @author Rayce
 */
@Service
public class StandardService extends ServiceImpl<StandardMapper, Standard> {
}
