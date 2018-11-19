package com.devinkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 来标注一个主程序类,说明这是一个主程序类
 */
@SpringBootApplication
public class HelloWorldMainApplication {
    public static void main(String[] args) {
        // Spring 应用启动起来
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }
}
