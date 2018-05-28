package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "DeleteProductByIdServlet", urlPatterns = {"/DeleteProductByIdServlet"})
public class DeleteProductByIdServlet extends HttpServlet {
    private ProductService productService = new ProductService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取商品id
         * 2. 调用service方法完成删除操作
         * 3. 重定向FindALlServlet
         */
        String pid = request.getParameter("pid");
        try {
            productService.deleteProductById(pid);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("msg", "删除商品信息失败");
            request.getRequestDispatcher("/case1/msg.jsp").forward(request,response);
            return;
        }
        response.sendRedirect(request.getContextPath() + "/FindAllServlet");
    }
}
