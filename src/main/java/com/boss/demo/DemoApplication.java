package com.boss.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//在配置多数据源的情况下需要加上exclude = DataSourceAutoConfiguration.class，排除自动注入数据源的配置（取消数据库配置），否则在初始化的时候程序不知道读取哪一个数据源报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.boss.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
