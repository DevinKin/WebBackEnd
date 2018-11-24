package com.devinkin.springboot02.controller;


import com.devinkin.springboot02.beean.User;
import com.devinkin.springboot02.component.NeoProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/getUser")
    public NeoProperties getUser() {
        User user = new User();
        user.setUserName("小明");
        user.setPassword("xxxx");
        NeoProperties neoProperties = new NeoProperties();
        return neoProperties;
//        return user;
    }
}
