package cn.devinkin.pro2.web.servlet;

import cn.devinkin.pro2.domain.User;
import cn.devinkin.pro2.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private UserService userService = new UserService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 接收两个验证码
         * 3. 接收用户名和密码
         * 4. 调用userService#getUsernameAndPassword()，返回User
         * 5. 判断user
         *  1. 若user为空
         *  2. 若user不为空
         *      1. 判断是否勾选了记住用户名，将user放入session中
         * 6. 重定向页面到index.jsp
         */

        //1
        request.setCharacterEncoding("utf-8");

        //2
        String rCode = request.getParameter("verifycode");
        String sCode = (String) request.getSession().getAttribute("sessionCode");

        //一次性验证码，使用完从session中移除
        request.getSession().removeAttribute("sessionCode");

        //判断两个验证码是否一致
        if (rCode == null || rCode.trim().length() == 0 || sCode == null) {
            //验证码有问题
            request.setAttribute("msg", "请重新输入验证码");
            request.getRequestDispatcher("/pro2/login.jsp").forward(request, response);
            return;
        }

        if (!rCode.equals(sCode)) {
            //验证码输入不一致
            request.setAttribute("msg", "请重新输入验证码");
            request.getRequestDispatcher("/pro2/login.jsp").forward(request,response);
            return;
        }

        //3
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = null;
        try {
            user = userService.getUserByUsernameAndPassword(username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (user == null) {
            request.setAttribute("msg", "用户名和密码不匹配");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            return;
        } else {
            if ("remeber".equals(request.getParameter("savename"))) {
                //创建cookie，username不能是中文
                Cookie cookie = new Cookie("username", username);
                cookie.setPath(request.getContextPath() + "/");
                cookie.setMaxAge(3600);

                //写回浏览器
                response.addCookie(cookie);
            }
            request.getSession().setAttribute("user",user);
            response.sendRedirect(request.getContextPath() + "/pro2/index.jsp");
        }

    }

}
