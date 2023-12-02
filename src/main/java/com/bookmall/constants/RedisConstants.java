package com.bookmall.constants;

/**
 * Redis使用的常量统一处理
 * @author Rayce
 */
public class RedisConstants {
    public static final String USER_TOKEN_KEY = "user:token:";
    public static final Integer USER_TOKEN_TTL = 180;

    public static final String BOOK_TOKEN_KEY = "book:id:";
    public static final Integer BOOK_TOKEN_TTL = 30;
}
