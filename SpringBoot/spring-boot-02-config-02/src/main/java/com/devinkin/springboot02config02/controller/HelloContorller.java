package com.devinkin.springboot02config02.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContorller {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
}
