<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午10:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>day13</title>
  </head>
  <body>
  <a href="${pageContext.request.contextPath}/model1/form.jsp">javabean在model1下的使用</a><br>
  <a href="<c:url value='/pro1/account.jsp'></c:url>">案例1</a><br>
  <a href="<c:url value='/pro2/account.jsp'></c:url>">案例2</a><br>
  </body>
</html>
