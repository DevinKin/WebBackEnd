<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-25
  Time: 下午6:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>转账案例</title>
</head>
<body>
<form action="<c:url value='/AccountServlet2'></c:url> " method="post">
    <table align="center" border="1">
        <tr>
            <td>转出方</td>
            <td><input type="text" name="fromuser"></td>
        </tr>
        <tr>
            <td>收款人</td>
            <td><input type="text" name="touser"></td>
        </tr>
        <tr>
            <td>转账金额</td>
            <td><input type="text" name="money"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="确认转账"></td>
        </tr>
        <tr>
            <td colspan="2">${msg}</td>
        </tr>
    </table>
</form>

</body>
</html>
