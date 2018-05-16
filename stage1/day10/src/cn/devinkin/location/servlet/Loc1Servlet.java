package cn.devinkin.location.servlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "Loc1Servlet", urlPatterns = {"/Loc1Servlet"})
public class Loc1Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("我没有零钱啊，你找助手借啊！");
        response.setStatus(302);
        response.setHeader("Location", "/Loc2Servlet");
    }
}
