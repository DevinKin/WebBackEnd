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
import java.lang.reflect.InvocationTargetException;

@WebServlet(name = "EditCustomerServlet", urlPatterns = "/editCustomer")
public class EditCustomerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String id = request.getParameter("cust_id");
        Customer c = new Customer();
        try {
            BeanUtils.populate(c, request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new CustomerServce().editCustomer(c, id);
        response.sendRedirect("/day28/showCustomers");
    }
}
