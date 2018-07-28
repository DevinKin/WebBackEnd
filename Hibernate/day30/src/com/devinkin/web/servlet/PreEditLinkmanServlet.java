package com.devinkin.web.servlet;

import com.devinkin.domain.Customer;
import com.devinkin.domain.Linkman;
import com.devinkin.service.CustomerServce;
import com.devinkin.service.LinkmanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PreEditLinkmanServlet", urlPatterns = "/preEditLinkman")
public class PreEditLinkmanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String slink_id = request.getParameter("lkm_id");
        Long link_id = Long.parseLong(slink_id);
        Linkman linkman = new LinkmanService().findById(link_id);
        request.setAttribute("linkman", linkman);

        List<Customer> customerList = new CustomerServce().showCustomers(null);
        request.setAttribute("list", customerList);
        request.getRequestDispatcher("/jsp/linkman/edit.jsp").forward(request,response);
    }
}
