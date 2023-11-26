package com.bookmall;

import com.sun.glass.ui.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookMallApplication {
    // DTO是展示层与服务层之间传递数据的对象
    // VO代表展示层需要显示的数据
    public static void main(String[] args) {
        SpringApplication.run(BookMallApplication.class, args);
    }
}
