package cn.devinkin.product.servlet;

import cn.devinkin.utils.CookieUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@WebServlet(name = "ProductHistoryServlet",urlPatterns = {"/ProductHistoryServlet"})
public class ProductHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取之前的浏览记录
         * 3. 判断cookie是否为空
         *  1. 若为空
         *  2. 若不为空
         * 4. 跳转到指定的商品页面
         */
        String id = request.getParameter("id");
        Cookie cookie = CookieUtils.getCookieByName("ids", request.getCookies());

        String ids = "";
        if (cookie == null) {
            //若cokie为空，需将当前商品id放入ids
            ids = id + "-";
        } else {
            //若cookie不为空，判断ids是否包含了该id
            //获取值
            ids = cookie.getValue();
            String[] arr = ids.split("-");
            //将数组转换成集合，此list长度
            List<String> asList = Arrays.asList(arr);
            LinkedList<String> list = new LinkedList<>(asList);
            if (list.contains(id)) {
                //若包含，将当前的id放到前面
                list.remove(id);
                list.addFirst(id);
            } else {
                //若不包含，判断长度是否>=3
                if (list.size() >= 3) {
                    //长度>=3，移除最后一个，将当前id放入最前面
                    list.removeLast();
                    list.addFirst(id);
                } else {
                    //长度<3，直接将当前id放入最前面
                    list.addFirst(id);
                }
            }

            //将list转换成字符串
            ids = "";
            for (String s : list) {
                ids+=(s + "-");
            }
        }
        //将ids写回去
        cookie = new Cookie("ids", ids.substring(0,ids.length() - 1));
        //设置访问路径
        cookie.setPath(request.getContextPath() + "/");
        //设置存活时间
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect(request.getContextPath() + "/shop/product_info" + id + ".htm");
    }
}
