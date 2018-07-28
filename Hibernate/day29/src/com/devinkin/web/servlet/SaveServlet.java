package com.devinkin.web.servlet;

import com.devinkin.domain.User;
import com.devinkin.service.UserService;

import java.io.IOException;

@javax.servlet.annotation.WebServlet(name = "SaveServlet", urlPatterns = "/saveServlet")
public class SaveServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        User u1 = new User();
        u1.setName("测试1");
        User u2 = new User();
        u2.setName("测试2");
        new UserService().save(u1, u2);
    }
}
