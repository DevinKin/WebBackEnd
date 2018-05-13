package cn.devinkin.test.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * ServletConfig常用方法
 */
@WebServlet(name = "SConfigServlet",urlPatterns = {"/SConfigServlet"},initParams = {
        @WebInitParam(name="user",value="root"),
        @WebInitParam(name="password",value="1234"),
})
public class SConfigServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取ServletConfig对象
         * 2. 获取servlet名称
         * 3. 获取初始化参数
         */
        ServletConfig servletConfig = this.getServletConfig();
        System.out.println(servletConfig.getServletName());
        System.out.println(servletConfig.getInitParameter("user"));
        System.out.println(servletConfig.getInitParameter("password"));
        Enumeration<String> names = servletConfig.getInitParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            System.out.println("参数名称: " + name);
            System.out.println("参数值: " + servletConfig.getInitParameter(name));
        }

    }
}
