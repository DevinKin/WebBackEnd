package cn.devinkin.pro1.web.servlet;

import cn.devinkin.pro1.domain.Product;
import cn.devinkin.pro1.service.ProductService;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "FindAllServlet", urlPatterns = {"/FindAllServlet"})
public class FindAllServlet extends javax.servlet.http.HttpServlet {

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        /**
         * 1. 调用service，返回list
         * 2. 将list放入request域中
         * 3. 请求转发
         */
        ProductService productService = new ProductService();
        try {
            List<Product> productList = productService.findAll();
            request.setAttribute("productList",productList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/pro1/product_list.jsp").forward(request,response);
    }
}
