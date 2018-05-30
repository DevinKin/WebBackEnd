package cn.devinkin.case4.web.servlet;

import cn.devinkin.case4.domain.City;
import cn.devinkin.case4.service.CityService;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "SelectCityServlet",urlPatterns = {"/SelectCityServlet"})
public class SelectCityServlet extends HttpServlet {
    private CityService cityService = new CityService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取请求参数pid
         * 2. 调用service通过pid获取相应省份的市区，返回一个list
         * 3. 将list转换成json，返回页面
         */
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String pid = request.getParameter("pid");
        List<City> list = null;
        try {
            list = cityService.findCitiesByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.getWriter().print(JSONArray.fromObject(list));
    }
}
