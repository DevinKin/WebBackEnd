package com.devinkin.web.servlet;

import com.devinkin.domain.Customer;
import com.devinkin.service.CustomerServce;

import java.io.IOException;
import java.util.List;

/**
 * 初始化到添加联系人的页面
 * @author king
 */
@javax.servlet.annotation.WebServlet(name = "com.devinkin.web.servlet.initAddServlet", urlPatterns = "/initAdd")
public class initAddServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        List<Customer> customerList = new CustomerServce().showCustomers(null);
        //保存到request域
        request.setAttribute("list", customerList);
        request.getRequestDispatcher("/jsp/linkman/add.jsp").forward(request,response);
    }
}
