<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-6-3
  Time: 上午10:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
</head>
<body>
<form action="<c:url value='/c3/upload1'></c:url>" method="post" enctype="multipart/form-data">
    用户名：<input type="text" name="username"><br>
    图片：<input type="file" name="img"><br>
    <input type="submit">
</form>
</body>
</html>
