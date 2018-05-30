package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.domain.User;
import cn.devinkin.case1.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckUsername4AjaxServlet", urlPatterns = {"/CheckUsername4AjaxServlet"})
public class CheckUsername4AjaxServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 0. 设置编码
         * 1. 获取请求参数
         * 2. 调用service方法查询用户
         * 3. 若用户为null，返回1，不为null，返回0
         */
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        User user = null;
        try {
            user = userService.findUserByName(username);
            if (user == null)
                response.getWriter().print("1");
            else
                response.getWriter().print("0");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 0. 设置编码
         * 1. 获取请求参数
         * 2. 调用service方法查询用户
         * 3. 若用户为null，返回1，不为null，返回0
         */
        String username = request.getParameter("username");
        username = new String(username.getBytes("iso-8859-1"),"utf-8");
        User user = null;
        try {
            user = userService.findUserByName(username);
            if (user == null)
                response.getWriter().print("1");
            else
                response.getWriter().print("0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
