package cn.devinkin.request.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 操作请求行
 */
@WebServlet(name = "RowServlet", urlPatterns = {"/RowServlet"})
public class RowServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取请求方式
        System.out.println(request.getMethod());

        //获取请求资源
        System.out.println(request.getRequestURI().toString());
        System.out.println(request.getRequestURL().toString());

        //获取请求参数的字符串
        System.out.println(request.getQueryString());

        //获取协议版本
        System.out.println(request.getProtocol());

        System.out.println("-------------掌握的方法--------------");

        //获取请求ip
        System.out.println(request.getRemoteAddr());

        //获取项目名
        System.out.println(request.getContextPath());
    }
}
