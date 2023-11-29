package com.bookmall.annotation;


import com.bookmall.annotation.enumUtils.AuthorityType;

import java.lang.annotation.*;

/**
 * Retention(RetentionPolicy.RUNTIME) 表示该注解在运行时保留，可以通过反射机制获取注解信息。
 * Target({ElementType.TYPE, ElementType.METHOD})表示该注解可以用于类和方法上。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface Authority {
    AuthorityType value() default AuthorityType.requireLogin;
}
