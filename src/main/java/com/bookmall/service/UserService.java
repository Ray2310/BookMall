package com.bookmall.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookmall.commonUtils.Result;
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
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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

    /**
     * 登录
     * @param loginForm
     * @return
     */
    public UserDTO login(LoginForm loginForm) {
        System.out.println(loginForm);
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
        //把用户token 存到redis中
        redisTemplate.opsForValue().set(RedisConstants.USER_TOKEN_KEY + token,user);
        //jwt不设置过期时间，只设置redis过期时间。
        redisTemplate.expire(RedisConstants.USER_TOKEN_KEY +token, RedisConstants.USER_TOKEN_TTL, TimeUnit.MINUTES);
        //把查到的user的一些属性赋值给userDTO
        UserDTO userDTO = BeanUtil.copyProperties(user,UserDTO.class);
        //设置token
        userDTO.setToken(token);
//        Long uuid = redisTemplate.getExpire(RedisConstants.USER_TOKEN_KEY + token);

        System.out.println("token====> " + token);
        //判断是否为管理员登陆
        return userDTO;
    }

    /**
     * 注册用户
     * @param loginForm
     * @return
     */
    public User register(LoginForm loginForm) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",loginForm.getUsername());
        User user = getOne(queryWrapper);
        if(user!=null){
            throw new ServiceException(Constants.CODE_403,"用户名已被使用");
        }else{
            user = new User();
            BeanUtils.copyProperties(loginForm,user);
            user.setNickname("新用户");
            user.setRole("user");
            save(user);
            return user;
        }
    }

    /**
     * 根据用户名查询数据库返回用户信息
     * 注意： 需要区别于 ORM封装的getOne
     * @param username
     * @return
     */
    public User getOne(String username){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return getOne(queryWrapper);
    }

    /**
     * 修改 or 新增用户
     * @param user
     * @return
     */
    public Result saveUpdate(User user) {
        if(user.getId() != null) {
            // 修改
            User old = this.baseMapper.selectById(user.getId());
            old.setNickname(ObjectUtils.isEmpty(user.getNickname()) ? old.getNickname() : user.getNickname());
            old.setAvatarUrl(ObjectUtils.isEmpty(user.getAvatarUrl()) ? old.getAvatarUrl() : user.getAvatarUrl());
            old.setRole(ObjectUtils.isEmpty(user.getRole()) ? old.getRole() : user.getRole());
            old.setPhone(ObjectUtils.isEmpty(user.getPhone()) ? old.getPhone() : user.getPhone());
            old.setEmail(ObjectUtils.isEmpty(user.getEmail()) ? old.getEmail() : user.getEmail());
            old.setAddress(ObjectUtils.isEmpty(user.getAddress()) ? old.getAddress() : user.getAddress());
            super.updateById(old);
            return Result.success("修改成功");
        } else {
            // 新增
            if(!ObjectUtils.isEmpty(this.getOne(user.getUsername()))) {
                return Result.error("400", "用户名已存在");
            }
            user.setPassword(user.getNewPassword());
            super.save(user);
            return Result.success("新增成功");
        }
    }
    /**
     * 重置密码
     *
     * @param id          用户id
     * @param newPassword 新密码
     */
    public void resetPassword(String id, String newPassword) {
        User user = this.getById(id);
        if(user == null) {
            return;
        }
        user.setPassword(newPassword);
        this.updateById(user);
    }
}
