package cn.devinkin.filter.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "BFilter",urlPatterns = {"/Demo3Servlet"})
public class BFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("BFilter收到了请求");
        chain.doFilter(req, resp);
        System.out.println("BFilter收到了响应");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
