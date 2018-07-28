package com.devinkin.web.servlet;

import com.devinkin.domain.Customer;
import com.devinkin.service.CustomerServce;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowCustomerServlet", urlPatterns = {"/showCustomers"})
public class ShowCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 现货区请求参数
        request.setCharacterEncoding("utf-8");
        // 获取到客户名称
        String custName = request.getParameter("custName");

        // 查询所有的方法的时候，传入进去
        List<Customer> customerList = new CustomerServce().showCustomers(custName);
        request.setAttribute("clist", customerList);
        request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request,response);
    }
}
