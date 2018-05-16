package cn.devinkin.request.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "ParameterServlet",urlPatterns = {"/ParameterServlet"})
public class ParameterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取单个请求参数
        System.out.println("username=" + request.getParameter("username"));

        //获取单个请求参数多个值
        String[] hobby = request.getParameterValues("hobby");
        System.out.print("hobby: ");
        System.out.println(Arrays.toString(hobby));

        // 获取多个请求参数多个值
        Map<String, String[]> parameter = request.getParameterMap();
        for (String key: parameter.keySet()) {
            System.out.println(key + "::" + Arrays.toString(parameter.get(key)));
        }
    }
}
