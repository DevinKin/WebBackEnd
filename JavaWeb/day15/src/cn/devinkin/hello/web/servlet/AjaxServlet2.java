package cn.devinkin.hello.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AjaxServlet2", urlPatterns = "/AjaxServlet2")
public class AjaxServlet2 extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String username = request.getParameter("username");
        username = new String(username.getBytes("iso-8859-1"),"utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print("Get: " + username);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print("Post: " + username);
    }
}
