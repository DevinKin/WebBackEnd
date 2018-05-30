package cn.devinkin.jqueryajax.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "JqueryAjaxServlet",urlPatterns = {"/JqueryAjaxServlet"})
public class JqueryAjaxServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = "{\"result\":\"success\",\"msg\":\"成功\"}";


        System.out.println("请求方式：" + request.getMethod());
        //接受参数
        String username = request.getParameter("username");
        System.out.println(username);
        //响应数据
        response.setContentType("text/html;charset=utf-8");
//        response.getWriter().print("success");
        response.getWriter().print(s);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("请求方式：" + request.getMethod());

        //接受参数
        String username = request.getParameter("username");
        username = new String(username.getBytes("iso-8859-1"),"utf-8");
        System.out.println(username);

        //响应数据
        response.getWriter().print("success");
    }
}
