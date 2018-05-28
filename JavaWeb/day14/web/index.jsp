<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-26
  Time: 下午11:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Day14案例</title>
  </head>
  <body>
  <h2><a href="<c:url value='/FindAllServlet'></c:url> ">展示所有商品</a></h2>
  <h2><a href="<c:url value='/case1/add.jsp'></c:url> ">添加商品</a></h2>
  <h2><a href="<c:url value='/ShowProductByPageServlet?currentPage=1'></c:url> ">分页展示商品</a></h2>
  </body>
</html>
