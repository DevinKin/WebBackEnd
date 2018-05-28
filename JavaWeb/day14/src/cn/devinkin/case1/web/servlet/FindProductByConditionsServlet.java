package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.domain.Product;
import cn.devinkin.case1.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FindProductByConditionsServlet",urlPatterns = {"/FindProductByConditionsServlet"})
public class FindProductByConditionsServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 接收请求参数
         * 3. 调用service#findProductByConditions()
         * 4. 将list放入request域中，请求转发
         */
        request.setCharacterEncoding("utf-8");
        String pname = request.getParameter("pname");
        String keyword = request.getParameter("keyword");
        List<Product> plist = null;
        try {
            plist = productService.findProductByConditions(pname, keyword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("plist", plist);
        request.getRequestDispatcher("/case1/product_list.jsp").forward(request, response);
    }

}
