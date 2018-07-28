package com.devinkin.web.servlet;

import com.devinkin.service.LinkmanService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteLinkmanServlet", urlPatterns = "/deleteLinkman")
public class DeleteLinkmanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String slkm_id = request.getParameter("lkm_id");
        Long lkm_id = Long.parseLong(slkm_id);
        new LinkmanService().deleteLinkman(lkm_id);
        response.sendRedirect("/day30/showLinkmans");
    }
}
