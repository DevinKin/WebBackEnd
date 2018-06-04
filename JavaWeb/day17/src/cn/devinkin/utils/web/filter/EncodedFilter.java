package cn.devinkin.utils.web.filter;

import cn.devinkin.utils.EncodedRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(filterName = "EncodedFilter",urlPatterns = "/*")
public class EncodedFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //强转
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        chain.doFilter(new EncodedRequest(request), response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
