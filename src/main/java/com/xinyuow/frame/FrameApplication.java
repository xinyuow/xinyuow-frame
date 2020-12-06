package com.xinyuow.frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xinyuow.frame.mapper")
@SpringBootApplication
public class FrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrameApplication.class, args);
    }

}
