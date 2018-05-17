package cn.devinkin.test.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LifeServlet", urlPatterns = {"/life"})
public class LifeServlet implements Servlet {
    /**
     * 初始化方法
     * 执行者：服务器
     * 执行次数：1次
     * 执行时间：默认第一次访问的时候
     * @param servletConfig
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("生命周期：init方法");

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    /**
     * 服务
     * 执行者：服务器
     * 执行次数：请求一次执行一次
     * 执行时机：请求到达的时候
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("生命周期：service方法");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    /**
     * 执行者：服务器
     * 执行次数：1次
     * 执行时间：当Servlet被移除或者服务器正常关闭的时候
     */
    @Override
    public void destroy() {
        System.out.println("生命周期：destroy方法");
    }
}
