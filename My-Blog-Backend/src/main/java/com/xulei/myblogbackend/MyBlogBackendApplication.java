package com.xulei.myblogbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.xulei.myblogbackend.mapper")
@SpringBootApplication
public class MyBlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBlogBackendApplication.class, args);
    }

}
