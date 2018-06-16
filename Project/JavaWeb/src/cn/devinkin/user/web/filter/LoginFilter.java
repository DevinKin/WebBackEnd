package cn.devinkin.user.web.filter;

import cn.devinkin.user.domain.User;
import cn.devinkin.user.service.UserService;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.CookieUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", servletNames = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1. 强转，使request可以获取session
         * 2. 判断有没有指定的cookie
         *  1. 有cookie，获取用户名和密码，调用service完成登录操作，将User放入session中
         * 3. 方形
         */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        User sUser = (User) request.getSession().getAttribute("user");
        if (sUser == null) {
            /**
             * 没有用户，需要自动登录
             * 1. 判断访问的资源是否与登录注册相关，若相关则不需要自动登录
             */
            String path = request.getRequestURI();
            if (!path.contains("user")) {
                /**
                 * 1. 判断cookie是否为空
                 *  1. 若不为空，获取username和password，调用servicee完成登录
                 * 2. 判断user是否为空，不为空，放入session
                 */
                Cookie autoLogincookie = CookieUtils.getCookieByName("autologin", request.getCookies());
                if (autoLogincookie != null) {
                    String username = autoLogincookie.getValue().split("-")[0];
                    String password = autoLogincookie.getValue().split("-")[1];
                    User user = null;
                    try {
                        user = ((UserService) BeanFactory.getBean("UserService")).login(username, password);
                        request.getSession().setAttribute("user", user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
