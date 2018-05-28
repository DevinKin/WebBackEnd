package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.domain.Product;
import cn.devinkin.case1.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * 展示所有商品
 */
@javax.servlet.annotation.WebServlet(name = "FindAllServlet", urlPatterns = {"/FindAllServlet"})
public class FindAllServlet extends javax.servlet.http.HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        /**
         * 1. 调用service，查询所有商品，返回list
         * 2. 将list放入request域中
         * 3. 请求转发
         */
        List<Product> plist = null;
        try {
            plist = productService.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("plist",plist);

        request.getRequestDispatcher("/case1/product_list.jsp").forward(request,response);
    }
}
