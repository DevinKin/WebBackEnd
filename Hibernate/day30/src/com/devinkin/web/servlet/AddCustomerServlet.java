package com.devinkin.web.servlet;

import com.devinkin.domain.Customer;
import com.devinkin.service.CustomerServce;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 添加客户的控制器
 * @author king
 */
@WebServlet(name = "AddCustomerServlet", urlPatterns = "/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 接收请求参数
         * 2. 接受请求参数
         * 3. 封装数据，使用BeanUtils工具，导入jar包
         * 4. 调用service层
         */
        request.setCharacterEncoding("utf-8");

        Map<String, String[]> map =  request.getParameterMap();

        Customer c = new Customer();
        try {
            BeanUtils.populate(c, map);
            new CustomerServce().addCustomer(c);
            System.out.println("添加客户成功");
            response.sendRedirect("/day30/showCustomers");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
