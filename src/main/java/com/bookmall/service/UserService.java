package com.bookmall.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.mapper.UserMapper;
import com.bookmall.domain.entity.User;
import org.springframework.stereotype.Service;

/**
 * 用户请求逻辑层实现
 * @author Rayce
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
