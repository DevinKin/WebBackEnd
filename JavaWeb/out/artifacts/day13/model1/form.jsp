<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="<c:url value='login.jsp'></c:url>" method="post">
    <table border="1" align="center">
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="password" name="password"></td>
        </tr>
        <tr colspan="2">
            <td><input type="submit"></td>
        </tr>
    </table>
</form>

</body>
</html>
