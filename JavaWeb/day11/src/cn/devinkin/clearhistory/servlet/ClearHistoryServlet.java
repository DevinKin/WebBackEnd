package cn.devinkin.clearhistory.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ClearHistoryServlet",urlPatterns = {"/ClearHistoryServlet"})
public class ClearHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 创建一个cookie，写回浏览器
         * 2. 设置cookie路径和名称保持一致
         * 3. 设置时间，清除该cookie
         * 4. 写回浏览器
         * 5. 重定向到product_list.jsp页面
         */
        Cookie cookie = new Cookie("ids", "");
        cookie.setPath(request.getContextPath() + "/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "shop/product_list.jsp");

    }
}
