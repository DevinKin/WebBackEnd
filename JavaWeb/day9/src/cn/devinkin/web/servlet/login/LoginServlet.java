package cn.devinkin.web.servlet.login;

import cn.devinkin.login.domain.User;
import cn.devinkin.login.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet",urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private UserService userService = new UserService();

    @Override
    public void init() throws ServletException {
        /**
         * 1. 获取ServletContext
         * 2. 初始化次数
         * 3. 将次数存入ServletContext中
         */
        ServletContext servletContext = this.getServletContext();
        servletContext.setAttribute("count", 0);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 接收用户名和密码
         * 2. 调用userService#User login(String username, String password)
         * 3. 判断user是否为空
         *  1. 若为空，用户名和密码不匹配
         *  2. 若不为空，"xxx:欢迎回来"
         */
        response.setContentType("text/html;charset=utf-8");
        //1.
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //2.
        User user = userService.login(username);

        //3.
        if (user == null || !password.equals(user.getPassword())) {
            response.getWriter().print("用户名和密码不匹配，3秒后跳转");
            response.setHeader("refresh", "3;url=/day9/login.htm");
        } else {
            response.getWriter().print(username + " :欢迎回来");
            ServletContext servletContext = this.getServletContext();
            Integer count = (Integer) servletContext.getAttribute("count");
            count++;
            servletContext.setAttribute("count", count);
        }

    }
}
