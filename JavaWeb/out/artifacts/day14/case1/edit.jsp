<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-27
  Time: 下午5:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>编辑商品</title>
</head>
<body>
<form action="<c:url value='/EditProductServlet'></c:url>" method="post">
    <table border="1" align="center" width="40%">
        <input type="hidden" name="pid" value="${product.pid}">
        <tr>
            <th>商品名称</th>
            <td><input type="text" name="pname" value="${product.pname}"></td>
        </tr>
        <tr>
            <th>市场价</th>
            <td><input type="text" name="market_price" value="${product.market_price}"></td>
        </tr>
        <tr>
            <th>商城价</th>
            <td><input type="text" name="shop_price" value="${product.shop_price}"></td>
        </tr>
        <tr>
            <th>商品描述</th>
            <td><input type="text" name="pdec" value="${product.pdec}"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="保存"></td>
        </tr>
    </table>
</form>

</body>
</html>
