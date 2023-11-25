package com.bookmall.constants;

/**
 * Redis使用的常量统一处理
 * @author Rayce
 */
public class RedisConstants {
    public static final String USER_TOKEN_KEY = "user:token:";
    public static final Integer USER_TOKEN_TTL = 180;

    public static final String GOOD_TOKEN_KEY = "good:id:";
    public static final Integer GOOD_TOKEN_TTL = 30;
}
