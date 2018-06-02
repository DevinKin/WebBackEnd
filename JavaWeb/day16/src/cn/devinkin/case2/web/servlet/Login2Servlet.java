package cn.devinkin.case2.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Login2Servlet",urlPatterns = {"/c2/login"})
public class Login2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取用户名和密码
         * 2. 打印在控制台
         */
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("password"));
    }
}
