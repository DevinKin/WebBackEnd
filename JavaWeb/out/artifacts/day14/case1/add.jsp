<%@ page import="cn.devinkin.case1.web.servlet.UUIDUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-27
  Time: 下午3:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加商品</title>
</head>
<body>
<%
    String code = UUIDUtils.UUID();

    //将code放入session中，后台需要验证
    session.setAttribute("token",code);
%>
<form action="<c:url value='/AddProductServlet'></c:url>" method="post">
    <input type="hidden" name="token" value="${token}">
    <table border="1" align="center" width="40%">
        <tr>
            <th>商品名称</th>
            <td><input type="text" name="pname"></td>
        </tr>
        <tr>
            <th>市场价</th>
            <td><input type="text" name="market_price"></td>
        </tr>
        <tr>
            <th>商城价</th>
            <td><input type="text" name="shop_price"></td>
        </tr>
        <tr>
            <th>商品描述</th>
            <td><input type="text" name="pdec"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="保存"></td>
        </tr>
    </table>
</form>

</body>
</html>
