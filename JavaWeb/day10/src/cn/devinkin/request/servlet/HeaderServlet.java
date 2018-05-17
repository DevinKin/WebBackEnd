package cn.devinkin.request.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HeaderServlet",urlPatterns = {"/HeaderServlet"})
public class HeaderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取浏览器内核
        System.out.println(request.getHeader("user-agent"));

        //获取referer
        String referer = request.getHeader("referer");
        if (referer == null) {
            System.out.println("直接在浏览器请求的");
        } else if (referer.contains("localhost")) {
            System.out.println("我自己点的");
        } else if (referer.contains("192.168")) {
            System.out.println("别人点的");
        }

    }
}
