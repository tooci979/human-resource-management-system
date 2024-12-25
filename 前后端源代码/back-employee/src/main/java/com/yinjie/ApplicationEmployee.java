package com.yinjie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/*
*
* 表示层：控制层和视图
* 业务层
* 数据访问层
* */

@EnableCaching  //用于开启 Spring 的缓存支持
@SpringBootApplication
public class ApplicationEmployee {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationEmployee.class, args);
    }
}
