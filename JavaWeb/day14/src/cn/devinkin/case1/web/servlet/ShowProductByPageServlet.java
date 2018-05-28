package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.domain.PageBean;
import cn.devinkin.case1.domain.Product;
import cn.devinkin.case1.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ShowProductByPageServlet", urlPatterns = {"/ShowProductByPageServlet"})
public class ShowProductByPageServlet extends HttpServlet {
    //固定每页显示的条目数
    private final static int PAGESIZE = 3;
    private ProductService productService = new ProductService();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取当前页码
         * 3. 调用service完成分页查询操作，返回PageBean
         * 4. 将PageBean放入request域中，请求转发到product_page.jsp
         */
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));

        PageBean<Product> pageBean = null;
        try {
            pageBean = productService.showProductsByPage(currentPage, PAGESIZE);
        } catch (SQLException e) {
            request.setAttribute("msg", "查询分页错误");
            request.getRequestDispatcher("/case1/msg.jsp").forward(request,response);
            return;
        }

        request.setAttribute("pb", pageBean);
        request.getRequestDispatcher("/case1/product_page.jsp").forward(request, response);

    }
}
