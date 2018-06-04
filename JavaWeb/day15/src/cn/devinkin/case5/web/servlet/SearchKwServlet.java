package cn.devinkin.case5.web.servlet;

import cn.devinkin.case5.service.KeyWordService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SearchKwServlet",urlPatterns = {"/SearchKwServlet"})
public class SearchKwServlet extends HttpServlet {
    private KeyWordService keyWordService = new KeyWordService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取kw参数
         * 3. 通过kw参数调用service进行模糊查询
         * 4. 将查询结果响应回ajax
         */
        //1
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        //2
        String kw = request.getParameter("kw");
        //3
        List<Object> list = null;
        try {
            list = keyWordService.getKeyWords4Ajax(kw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //4
        if (list != null && list.size() > 0) {
            String resp = list.toString();
            resp = resp.substring(1,resp.length() - 1);
            response.getWriter().print(resp);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取kw参数
         * 3. 通过kw参数调用service进行模糊查询
         * 4. 将查询结果响应回ajax
         */
        //1
        response.setContentType("text/html;charset=utf-8");
        //2
        String kw = request.getParameter("kw");
        kw = new String(kw.getBytes("iso-8859-1"), "utf-8");
        //3
        List<Object> list = null;
        try {
            list = keyWordService.getKeyWords4Ajax(kw);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //4
        if (list != null && list.size() > 0) {
            String resp = list.toString();
            resp = resp.substring(1,resp.length() - 1);
            response.getWriter().print(resp);
        }
    }
}
