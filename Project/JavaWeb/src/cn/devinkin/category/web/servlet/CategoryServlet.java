package cn.devinkin.category.web.servlet;

import cn.devinkin.category.domain.Category;
import cn.devinkin.category.service.CategoryService;
import cn.devinkin.category.service.impl.CategoryServiceImpl;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.JsonUtil;
import net.sf.json.util.JSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", urlPatterns = "/category")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");

    /**
     * 查询所有分类
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 调用categoryService查询所有分类，返回值为list<Category>
         * 2. 将返回值转换为json格式，返回到页面上
         */
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = JsonUtil.list2json(categoryList);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(json);
        return null;
    }
}
