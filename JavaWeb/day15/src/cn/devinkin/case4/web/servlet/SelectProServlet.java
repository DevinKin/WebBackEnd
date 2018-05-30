package cn.devinkin.case4.web.servlet;

import cn.devinkin.case4.domain.Province;
import cn.devinkin.case4.service.ProvinceService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SelectProServlet",urlPatterns = {"/SelectProServlet"})
public class SelectProServlet extends HttpServlet {
    private ProvinceService provinceService = new ProvinceService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 调用service查询所有省份
         * 2. 将所有的省份回写
         */
        List<Province> list = null;
        try {
            list = provinceService.findAllPro();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html;charset=utf-8");
        String json = "";
        if (list != null && list.size() > 0) {
            response.getWriter().print(JSONArray.fromObject(list));
        }
    }
}
