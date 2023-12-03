package com.bookmall.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bookmall.commonUtils.UserHolder;
import com.bookmall.constants.Constants;
import com.bookmall.constants.RedisConstants;
import com.bookmall.domain.entity.User;
import com.bookmall.exception.ServiceException;
import com.bookmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


/*
第一层拦截器，验证用户token,把redis中的user存到threadlocal
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    RedisTemplate<String, User> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Token");
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
//        System.out.println("1. request: " + request.getHeader("token"));
//        System.out.println("2. response: " + response);
//        System.out.println("3. token: " + token);
        //验证是否有token
        if(!StringUtils.hasLength(token)){
            throw  new ServiceException(Constants.TOKEN_ERROR,"token失效,请重新登陆");
        }
        //通过token，将redis中的user存到threadlocal（UserHolder）
        User user = redisTemplate.opsForValue().get(RedisConstants.USER_TOKEN_KEY + token);
//        System.out.println("user: "  +user);
        if(user == null){
            throw  new ServiceException(Constants.TOKEN_ERROR,"token失效,请重新登陆");
        }

        UserHolder.saveUser(user);
        //重置过期时间
        redisTemplate.expire(RedisConstants.USER_TOKEN_KEY +token, RedisConstants.USER_TOKEN_TTL, TimeUnit.MINUTES);
        //验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUsername())).build();
        try {
            jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
            throw new ServiceException(Constants.TOKEN_ERROR,"token验证失败，请重新登陆");
        }
        return true;
    }
}
