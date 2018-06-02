package cn.devinkin.filter.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "LifeFilter",urlPatterns = {"/Demo2Servlet"})
public class LifeFilter implements Filter {
    public void destroy() {
        System.out.println("Filter销毁了");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter放行了");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Filter初始化了");
    }

}
