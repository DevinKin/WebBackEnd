package cn.devinkin.hello.web.servlet;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "AjaxServlet1", urlPatterns = {"/AjaxServlet1"})
public class AjaxServlet1 extends javax.servlet.http.HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("请求来了~~~");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print("响应给你了喔~~");
    }
}
