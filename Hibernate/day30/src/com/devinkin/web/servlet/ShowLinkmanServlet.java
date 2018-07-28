package com.devinkin.web.servlet;

import com.devinkin.domain.Linkman;
import com.devinkin.service.LinkmanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowLinkmanServlet", urlPatterns = "/showLinkmans")
public class ShowLinkmanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //获取筛选的名字
        String name = request.getParameter("lkm_name");

        List<Linkman> linkmanList = new LinkmanService().findAll(name);
        request.setAttribute("list", linkmanList);
        request.getRequestDispatcher("/jsp/linkman/list.jsp").forward(request,response);
    }
}
