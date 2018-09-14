<%--
  Created by IntelliJ IDEA.
  User: devinkin
  Date: 2018/9/14
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<form action="${pageContext.request.contextPath}/login/submit" method="post">
    <table>
        <tr>
            <td>用户名: <input type="text" name="username" value=""/></td>
        </tr>
        <tr>
            <td>用户名: <input type="password" name="pwd" value=""/></td>
        </tr>
        <tr>
            <td><input type="submit" value="登录"></td>
        </tr>
    </table>
</form>
<body>

</body>
</html>
