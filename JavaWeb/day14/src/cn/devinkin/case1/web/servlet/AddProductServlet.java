package cn.devinkin.case1.web.servlet;

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
import java.sql.SQLException;
import java.util.Date;

/**
 * 添加商品
 */
@WebServlet(name = "AddProductServlet", urlPatterns = {"/AddProductServlet"})
public class AddProductServlet extends HttpServlet {
    private ProductService productService = new ProductService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 封装数据
         * 3. 调用service完成添加操作
         * 4. 页面跳转
         */
        request.setCharacterEncoding("utf-8");

        /**
         * 扩展令牌机制
         * 1. 获取session中的和提交过来的令牌
         * 2. 移除session中的令牌
         * 3. 比较两个令牌
         */
        String rToken = request.getParameter("token");
        String sToken = (String) request.getSession().getAttribute("token");
        System.out.println("rtoken: " + rToken);
        System.out.println("stoken: " + sToken);
        request.getSession().removeAttribute("stoken");
        if (sToken == null || !sToken.equals(rToken)) {
            //已经提交过了，生成错误信息，放入request域中，跳转到msg.jsp
            request.setAttribute("msg","商品已经保存");
            request.getRequestDispatcher("/case1/msg.jsp").forward(request,response);
            return;
        }
        Product product = new Product();
        try {
            BeanUtils.populate(product, request.getParameterMap());

            //给商品设置pid
            product.setPid(UUIDUtils.UUID());

            //设置时间
            product.setPdate(new Date());
            productService.addProduct(product);
            //页面跳转
            response.sendRedirect(request.getContextPath() + "/FindAllServlet");
            System.out.println("test");

        } catch (Exception e) {
            request.setAttribute("msg", "添加商品失败");
            request.getRequestDispatcher("/case1/msg.jsp").forward(request,response);
            e.printStackTrace();
        }

    }

}
