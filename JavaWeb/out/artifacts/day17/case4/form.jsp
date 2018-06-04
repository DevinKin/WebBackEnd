<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-6-3
  Time: 下午10:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>案例4-使用动态代理处理统一编码</title>
</head>
<body>
<form action="<c:url value='/c4/login'></c:url> " method="get">
    用户名：<input type="text" name="username"><br>
    备注：<input type="text" name="memo"><br>
    <input type="submit">
</form>

<hr>
这里是post<br>
<form action="<c:url value='/c4/login'></c:url> " method="post">
    用户名：<input type="text" name="username"><br>
    备注：<input type="text" name="memo"><br>
    <input type="submit">
</form>

</body>
</html>
