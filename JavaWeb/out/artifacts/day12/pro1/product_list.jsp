<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午7:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>product_list</title>
</head>
<body>
<table border="1" align="center">
    <tr>
        <td>id</td>
        <td>商品名称</td>
        <td>商品价格</td>
        <td>商品描述</td>
    </tr>
    <c:forEach items="${productList}" var="product">
        <tr>
            <td>${product.id}</td>
            <td>${product.pname}</td>
            <td>${product.price}</td>
            <td>${product.pdsc}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
