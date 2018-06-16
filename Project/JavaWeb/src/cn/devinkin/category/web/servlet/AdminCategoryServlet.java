package cn.devinkin.category.web.servlet;

import cn.devinkin.category.domain.Category;
import cn.devinkin.category.service.CategoryService;
import cn.devinkin.order.service.OrderService;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet", urlPatterns = "/adminCategory")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 调用service方法获取所有分类
         * 2. 将categoryList放入request域中，请求转发
         */
        List<Category> categoryList = null;
        try {
            categoryList = categoryService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("categories", categoryList);
        return "f:/store/admin/category/list.jsp";
    }


    /**
     * 添加分类的页面
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "f:/store/admin/category/add.jsp";
    }


    /**
     * 添加分类
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 接收cname参数
         * 2. 获取cid，封装Category
         * 3. 调用service方法添加分类
         */
        String cname = request.getParameter("cname");
        String cid = UUIDUtils.getId();
        Category category = new Category();
        category.setCid(cid);
        category.setCname(cname);
        try {
            categoryService.addCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/adminCategory?method=findAll";
    }


    /**
     * 编辑分类页面
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String editCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取cid
         * 2. 调用service#getCategoryByCid获取分类
         * 3. 将分类保存到request域中
         * 4. 请求转发到edit.jsp
         */
        String cid = request.getParameter("cid");
        Category category = null;
        try {
            category = categoryService.getCategoryByCid(cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("category", category);
        return "/store/admin/category/edit.jsp";
    }


    /**
     * 编辑分类
     * @param request
     * @param response
     * @return 请求转发路径
     * @throws ServletException
     * @throws IOException
     */
    public String editCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取cid
         * 2. 获取cname
         * 3. 封装Category
         * 4. 调用service#updateCategory更新分类
         * 5. 重定向到所有分类页面
         */
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        Category category = new Category();
        category.setCid(cid);
        category.setCname(cname);
        try {
            categoryService.updateCategory(category);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "r:/adminCategory?method=findAll";
    }


    /**
     * 删除分类
     * @param request
     * @param response
     * @return 重定向路径
     * @throws ServletException
     * @throws IOException
     */
    public String deleteCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取cid
         * 2. 调用service#delteCategory删除分类
         * 3. 重定向到分类首页
         */
        String cid = request.getParameter("cid");
        try {
            categoryService.deleteCategory(cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "r:/adminCategory?method=findAll";
    }
}
