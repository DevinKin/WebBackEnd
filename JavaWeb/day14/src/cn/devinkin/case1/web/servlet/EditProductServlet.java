package cn.devinkin.case1.web.servlet;

import cn.devinkin.case1.dao.ProductDao;
import cn.devinkin.case1.domain.Product;
import cn.devinkin.case1.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 1. 修改商品
 */
@WebServlet(name = "EditProductServlet", urlPatterns = {"/EditProductServlet"})
public class EditProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 封装数据
         * 3. 调用service完成更新操作
         * 4. 重定向到FindAllServlet
         */
        request.setCharacterEncoding("utf-8");
        Product product = new Product();
        try {
            BeanUtils.populate(product, request.getParameterMap());
            productService.updateProduct(product);
            response.sendRedirect(request.getContextPath() + "/FindAllServlet");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "修改商品信息错误");
            request.getRequestDispatcher("/case1/msg.jsp").forward(request,response);
        }
    }
}
