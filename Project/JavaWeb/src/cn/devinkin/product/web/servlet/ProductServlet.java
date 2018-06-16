package cn.devinkin.product.web.servlet;

import cn.devinkin.product.domain.PageBean;
import cn.devinkin.product.domain.Product;
import cn.devinkin.product.service.ProductService;
import cn.devinkin.product.service.impl.ProductServiceImpl;
import cn.devinkin.utils.BaseServlet;
import cn.devinkin.utils.BeanFactory;
import cn.devinkin.utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends BaseServlet {
    private ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
    /**
     * 通过pid查找商品
     * @param request
     * @param response
     * @return 商品
     * @throws ServletException
     * @throws IOException
     */
    public String getProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取商品id
         * 2. 调用service查找获得商品
         * 3. 将商品放入request域中
         * 4. 请求转发到product_info.jsp
         */
        String pid = request.getParameter("pid");
        Product product = null;
        try {
            product = productService.getProductByPid(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("p_info", product);

        /**
         * 1. 获取指定的cookie，判断cookie是否为空
         *  1. 若为空，创建cookie，名为ids，值为pid
         *  2. 若不为空
         *      1. 获取cookie的value值
         *      2. 判断value是否contain(包含)pid，将字符串切割，转换为list
         *          1. 若有
         *              1. 先移除，然后将商品id放到最前面
         *          2. 若没有
         *              1. 继续判断list长度是否大于等于3
         *                  1. 若大于等于6：移除list最后一个，将pid放入第一位
         *                  2. 若小于6：直接将pid插入到第一位即可
         * 2. 回写cookie
         */
        String ids = "";
        Cookie cookie = CookieUtils.getCookieByName("ids",request.getCookies());
        if (cookie == null) {
            ids = pid + "-";
        } else {
            //将字符串转换为list
            ids = cookie.getValue();
            List<String> list = Arrays.asList(ids.split("-"));
            LinkedList<String> slist = new LinkedList<>(list);

            if (slist.contains(pid)) {
                slist.remove(pid);
                slist.addFirst(pid);
            } else {
                if (slist.size() >= 6) {
                    slist.removeLast();
                }
                slist.addFirst(pid);
            }
            //将list转换为字符串
            ids = "";
            for (String s : slist) {
                ids += (s + "-");
            }
        }
        cookie = new Cookie("ids",ids);
        cookie.setMaxAge(3600);
        cookie.setPath(request.getContextPath() + "/");
        response.addCookie(cookie);
        response.setContentType("text/html;charset=utf-8");

        return "f:/store/jsp/product_info.jsp";
    }

    /**
     * 分页查询商品
     * @param request
     * @param response
     * @return 商品列表
     * @throws ServletException
     * @throws IOException
     */
    public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 获取分类id
         * 2. 获取当前页
         * 3. 设置一个pageSIze
         * 4. 调用service，返回值为PageBean
         * 5. 将PageBean保存到request域中
         * 6. 请求转发到product_list.jsp
         */
        String cid = request.getParameter("cid");
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        final int pageSize = 12;

        PageBean<Product> pageBean = null;
        try {
            pageBean = productService.findByPageWithFlag(currentPage, pageSize,cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("pb", pageBean);
        return "f:/store/jsp/product_list.jsp";
    }
}
