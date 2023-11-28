package com.bookmall.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.config.TokenUtils;
import com.bookmall.constants.Constants;
import com.bookmall.constants.RedisConstants;
import com.bookmall.domain.dto.UserDTO;
import com.bookmall.domain.entity.LoginForm;
import com.bookmall.exception.ServiceException;
import com.bookmall.mapper.UserMapper;
import com.bookmall.domain.entity.User;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import com.sun.org.apache.bcel.internal.generic.DCONST;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 用户请求逻辑层实现
 * @author Rayce
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Resource
    RedisTemplate<String,User> redisTemplate;

    public UserDTO login(LoginForm loginForm) {

        //用queryWrapper验证登录用户的username 和 password
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginForm.getUsername());
        queryWrapper.eq("password",loginForm.getPassword());
        User user = getOne(queryWrapper);
        if(user == null) {
            throw new ServiceException(Constants.CODE_403,"用户名或密码错误");
        }
        // 然后将用户登录凭证缓存到redis中， 并 设置ttl
        String token = TokenUtils.genToken(user.getId().toString(), user.getUsername());
        //把用户存到redis中
        redisTemplate.opsForValue().set(RedisConstants.USER_TOKEN_KEY + token,user);
        //jwt不设置过期时间，只设置redis过期时间。
        redisTemplate.expire(RedisConstants.USER_TOKEN_KEY +token, RedisConstants.USER_TOKEN_TTL, TimeUnit.MINUTES);
        //把查到的user的一些属性赋值给userDTO
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
        //设置token
        userDTO.setToken(token);
        Long uuid = redisTemplate.getExpire(RedisConstants.USER_TOKEN_KEY + token);

        System.out.println("token====> " + token);
        System.out.println("uuid ====> " + uuid);
        return userDTO;


    }
}
