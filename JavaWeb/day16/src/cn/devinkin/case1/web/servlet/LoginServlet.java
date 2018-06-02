package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.constant.Constant;
import cn.devinkin.case1.domain.User;
import cn.devinkin.case1.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet(name = "LoginServlet",urlPatterns = {"/c1/login"})
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取用户名和密码
         * 3. 调用service
         * 4. 判断User是否为空
         * 5. 若不为空，跳转到success.jsp页面
         */
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.Login(username,password);

        if (user == null) {
            request.setAttribute("msg", "用户名和密码不匹配");
            request.getRequestDispatcher("/case1/login.jsp").forward(request,response);
            return;
        } else {
            request.getSession().setAttribute("user",user);

            /**
             * 判断是否勾选了自动登录
             * 若勾选了，需要将用户和密码放入cookie中，写回浏览器中
             */
            if (Constant.IS_AUTO_LOGIN.equals(request.getParameter("autoLogin"))) {
                //创建Cookie
                Cookie cookie = new Cookie("autologin", URLEncoder.encode(username , "utf-8") + "-" + password);
                cookie.setMaxAge(3600);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }

            /**
             * 判断是否勾选了记住用户名
             * 若勾选了，需要加将用户名放入cookie中，写回浏览器
             */
            if (Constant.IS_SAVE_NAME.equals(request.getParameter("saveName"))) {
                //创建Cookie
                Cookie cookie = new Cookie("savename", URLEncoder.encode(username, "utf-8"));
                cookie.setMaxAge(3600);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }

            //页面重定向
            response.sendRedirect(request.getContextPath() + "/case1/success.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
