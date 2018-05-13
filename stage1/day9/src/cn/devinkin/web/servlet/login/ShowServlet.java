package cn.devinkin.web.servlet.login;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "ShowServlet", urlPatterns = {"/show"},loadOnStartup = 1)
@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取全局管理者
         * 3. 获取登录次数
         * 4. 在页面上打印总次数
         */
        ServletContext context = this.getServletContext();
        response.setContentType("text/html;charset=utf-8");
        Integer count = (Integer) context.getAttribute("count");
        response.getWriter().print("登录成功的总次数为：" + count);
    }
}
