package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(name = "DeleteCheckedProductServlet", urlPatterns = {"/DeleteCheckedProductServlet"})
public class DeleteCheckedProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取pids
         * 2. 调用service的deleteProducts
         * 3. 页面重定向到FindAllServlet
         */
        String[] pids = request.getParameterValues("pid");
        System.out.println(Arrays.toString(pids));
        try {
            productService.deleteProducts(pids);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/FindAllServlet");
    }
}
