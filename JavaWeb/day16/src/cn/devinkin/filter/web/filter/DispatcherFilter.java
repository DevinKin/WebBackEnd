package cn.devinkin.filter.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "DispatcherFilter", urlPatterns = {"/e/*"}, dispatcherTypes = {DispatcherType.REQUEST,DispatcherType.FORWARD})
public class DispatcherFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("DispatcherFilter执行了");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
