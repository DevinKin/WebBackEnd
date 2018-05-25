<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-17
  Time: 下午1:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <table border="1" align="center" width="20%">
        <tr>
            <td>商品名称</td>
            <td>商品数量</td>
        </tr>
        <%
            /**
             * 1. 获取购物车
             * 2. 判断购物车是否为空
             *  1. 若为空：亲，购物车空空如也
             *  2. 若不为空：遍历购物车
             */
            Map<String,Integer> cart = (Map<String, Integer>) session.getAttribute("cart");

            if (cart == null) {
                out.print("<tr><td colspan='2'>亲，购物车空空如也</td></tr>");
            } else {

                for (String name : cart.keySet()) {
                    out.print("<tr>");
                    out.print("<td>");
                    out.print(name);
                    out.print("</td>");
                    out.print("<td>");
                    out.print(cart.get(name));
                    out.print("</td>");
                    out.print("</tr>");
                }
            }
        %>
        <hr>
        <br>
        <center>
            <a href="/day11/shop/product_list.jsp">继续购物</a>&nbsp;&nbsp;&nbsp;&nbsp;
            <a href="/day11/ClearCartServlet">清空购物车</a>
        </center>
    </table>
</body>
</html>
