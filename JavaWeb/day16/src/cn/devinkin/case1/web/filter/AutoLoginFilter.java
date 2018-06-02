package cn.devinkin.case1.web.filter;

import cn.devinkin.case1.domain.User;
import cn.devinkin.case1.service.UserService;
import cn.devinkin.utils.CookieUtils;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = {"/*"})
public class AutoLoginFilter implements Filter {
    private UserService userService = new UserService();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1. 强转，使request可以获取session
         * 2. 判断有没有指定cookie
         *  1. 有cookie，获取用户名和密码，调用service完成登录操作，将User放入session中
         * 3. 放行
         */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        /**
         * 完成自动登录
         * 1. 判断session中有无登录用户
         *  1. 没有，自动登录
         */
        User sUser = (User) request.getSession().getAttribute("user");
        if ( sUser == null) {
            //没有用户，需要自动登录
            /**
             * 判断访问的资源是否与登录注册相关，若相关则不需要自动登录
             */
            String path = request.getRequestURI();      // /day16/xxx
            if (!path.contains("/login") || !path.contains("/regist")) {
                Cookie cookie =  CookieUtils.getCookieByName("autologin", request.getCookies());

                /**
                 * 1. 判断cookie是否为空
                 *  1. 若不为空，获取值(username和password)调用service完成登录，判断user是否为空，不为空，放入session
                 */
                if (cookie != null) {
                    String username = cookie.getValue().split("-")[0];
                    String password = cookie.getValue().split("-")[1];
                    User user = userService.Login(username,password);
                    if (user != null) {
                        //将user放入session中
                        request.getSession().setAttribute("user",user);
                    }
                }

            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
