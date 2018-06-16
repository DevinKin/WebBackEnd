<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-6-6
  Time: 下午9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<c:url value='/testFilter'></c:url> " method="get">
    用户名：<input type="text" name="username"><br>
    性别：<input type="text" name="sex"><br>
    密码：<input type="password" name="password"><br>
    <input type="submit">
</form>
</body>
</html>
