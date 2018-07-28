package com.devinkin.web.servlet;

import com.devinkin.domain.Linkman;
import com.devinkin.service.LinkmanService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.IOException;
import java.util.List;

/**
 * 查询所有的联系人
 * @author king
 */
@javax.servlet.annotation.WebServlet(name = "ListLinkmanServlet", urlPatterns = "/listLinkmans")
public class ListLinkmanServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //解决中文乱码问题
        request.setCharacterEncoding("utf-8");
        String lkm_name = request.getParameter("lkm_name");
        DetachedCriteria criteria = DetachedCriteria.forClass(Linkman.class);
        if (lkm_name != null && !lkm_name.trim().isEmpty()) {
            criteria.add(Restrictions.like("lkm_name", "%" + lkm_name + "%"));
        }
        List<Linkman> linkmans = new LinkmanService().findAll(criteria);
        request.setAttribute("linkmans", linkmans);

        request.getRequestDispatcher("/jsp/linkman/list.jsp").forward(request,response);
    }
}
