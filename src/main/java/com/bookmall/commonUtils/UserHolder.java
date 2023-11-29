package com.bookmall.commonUtils;


import com.bookmall.domain.entity.User;

/**
 * 用户信息线程，将当前用户保存到一个线程中
 * 可以在多线程环境中实现线程隔离的用户对象存储。每个线程都可以独立地保存和获取自己的用户对象，而不会相互干扰。
 */
public class UserHolder {
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void saveUser(User user){
        userThreadLocal.set(user);
    }

    public static User getUser(){
        return userThreadLocal.get();
    }

    public static void removeUser(){
        userThreadLocal.remove();
    }
}
