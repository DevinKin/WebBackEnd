package cn.devinkin.web.servlet;

import cn.devinkin.category.service.CategoryService;
import cn.devinkin.category.service.impl.CategoryServiceImpl;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.product.service.impl.ProductServiceImpl;
import cn.devinkin.utils.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 和首页相关的servlet
 */
@WebServlet(name = "IndexServlet",urlPatterns = "/index")
public class IndexServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //去数据库中查询最新商品和热门商品，将结果放入request域中，请求转发到/jsp/index.jsp

        //最新商品
        List<Product> newLists = null;
        List<Product> hotLists = null;
        try {
            newLists = productService.findNewest();
            hotLists = productService.findHotest();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //热门商品

        //经两个list放入request域中
        request.setAttribute("nlist", newLists);
        request.setAttribute("hlist", hotLists);
        return "f:/store/jsp/index.jsp";
    }
}
