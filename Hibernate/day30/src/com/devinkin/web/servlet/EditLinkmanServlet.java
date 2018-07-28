package com.devinkin.web.servlet;


import com.devinkin.domain.Linkman;
import com.devinkin.service.LinkmanService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "EditLinkmanServlet", urlPatterns = "/EditLinkman")
public class EditLinkmanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 解决中文乱码问题
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();

        //获取客户id值
        String scust_id = map.get("cust_id")[0];
        Long cust_id = Long.parseLong(scust_id);

        Linkman linkman = new Linkman();
        try {
            //封装数据到联系人中
            BeanUtils.populate(linkman, map);
            new LinkmanService().save(linkman, cust_id);
            response.sendRedirect("/day30/showLinkmans");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
