package cn.devinkin.cart.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "add2CartServlet", urlPatterns = {"/add2CartServlet"})
public class add2CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 1. 设置编码
         * 2. 获取商品的名称
         * 3. 将商品添加到购物车
         * 4. 提示信息
         */
        response.setContentType("text/html;charset=utf-8");
        PrintWriter w = response.getWriter();

        String name =request.getParameter("name");
        name = new String(name.getBytes("ISO-8859-1"), "utf-8");

        /**
         * 2. 将商品添加到购物车
         *  1. 从session中获取购物车
         *  2. 判断购物车是否为空
         */
        Integer count = null;
        Map<String, Integer> cart = (Map<String, Integer>) request.getSession().getAttribute("cart");
        if (cart == null) {
            //第一次购物，创建购物车
            cart = new HashMap<String, Integer>();

            count = 1;
            cart.put(name,count);

            //将商品放入购物车
            request.getSession().setAttribute("cart", cart);
        } else {
            //购物车不为空，继续判断购物车中是否有该商品
            count = cart.get(name);
            if (count == null) {
                // 购物车中没有该商品
                count = 1;
            } else {
                //购物车有该商品
                count++;
            }
        }
        cart.put(name,count);

        //提示信息
        w.print("已经将<b>" + name + "</b>添加到购物车中");
        w.print("&nbsp;&nbsp;&nbsp;&nbsp;");
        w.print("<a href='" + request.getContextPath() +"/shop/product_list.jsp'>继续购物</a>&nbsp;&nbsp;&nbsp;&nbsp;");
        w.print("<a href='" + request.getContextPath() +"/shop/cart.jsp'>查看购物车</a>&nbsp;&nbsp;&nbsp;&nbsp;");
    }
}
