package com.Morgan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author Morgan
 * @create 2022-10-14-22:43
 */
@SpringBootApplication
//@MapperScan("org.example.shopmanager.mapper")
public class BiliBiliApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(BiliBiliApp.class, args);


    }
}
