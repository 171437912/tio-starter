package com.yingke.tiostarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yingke.tiostarter.http")
public class TioStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(TioStarterApplication.class, args);
    }

}
