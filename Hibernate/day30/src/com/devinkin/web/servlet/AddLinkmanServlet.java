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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 添加联系人
 * @author king
 */
@WebServlet(name = "AddLinkmanServlet", urlPatterns = "/addLinkman")
public class AddLinkmanServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 先解决中文乱码
        request.setCharacterEncoding("utf-8");

        // 接收数据
        Map<String, String[]> map = request.getParameterMap();

        // 获取客户的id
        String scust_id = map.get("cust_id")[0];
        //转换
        Long cust_id = Long.parseLong(scust_id);

        //可以封装数据
        Linkman linkman = new Linkman();
        try {
            //封装数据
            BeanUtils.populate(linkman, map);

            // 调用业务层保存联系人
            new LinkmanService().save(linkman, cust_id);
            System.out.println("保存联系人成功");
            request.getRequestDispatcher("/jsp/linkman/list.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
