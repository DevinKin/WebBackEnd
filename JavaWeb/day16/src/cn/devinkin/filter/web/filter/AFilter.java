package cn.devinkin.filter.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "AFilter", urlPatterns = {"/Demo3Servlet"})
public class AFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("AFilter收到了请求");
        chain.doFilter(req, resp);
        System.out.println("AFilter收到了响应");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
