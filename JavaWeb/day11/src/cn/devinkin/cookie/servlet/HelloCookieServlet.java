package cn.devinkin.cookie.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import java.io.IOException;

@WebServlet(name = "HelloCookieServlet",urlPatterns = {"/HelloCookieServlet"})
public class HelloCookieServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        /**
         * 设置编码
         * 1. 创建一个cookie
         * 2. 写回浏览器
         * 3. 提示信息
         *
         */
        response.setContentType("text/html;charset=utf-8");
        Cookie cookie = new Cookie("akey", "avalue");
        Cookie cookieB = new Cookie("bkey", "bvblue");
        Cookie cookieC = new Cookie("ckey", "cvclue");
        response.addCookie(cookie);
        response.addCookie(cookieB);
        response.addCookie(cookieC);
        response.getWriter().print("cookie 已写回");
    }
}
