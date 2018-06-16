package cn.devinkin.utils;

import cn.devinkin.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "PrivilegeFilter",
        urlPatterns = {"/store/jsp/order_info.jsp", "/store/jsp/order_list.jsp", "/store/jsp/cart.jsp",},
        servletNames = {"OrderServlet", "CartServlet"})
public class PrivilegeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        /**
         * 1. 强转
         * 2. 业务逻辑
         * 3. 放行
         */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //从session中获取user，判断user是否为空，若为空，请求转发
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "没有权限，请先登录!");
            request.getRequestDispatcher("/store/jsp/msg.jsp").forward(request, response);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
