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

@WebServlet(name = "GetProductByIdServlet",urlPatterns = {"/GetProductByIdServlet"})
public class GetProductByIdServlet extends HttpServlet {
    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取商品pid
         * 3. 调用service，通过id获取商品，返回值为product
         * 4. 将product放入request域中，请求转发到edit.jsp
         */
        String pid = request.getParameter("pid");
        Product product = null;
        try {
            product = productService.getProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (product == null) {
            request.getRequestDispatcher("/case1/edit.jsp").forward(request,response);
            return;
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher("/case1/edit.jsp").forward(request,response);
    }
}
