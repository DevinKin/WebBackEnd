<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-6-1
  Time: 下午4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户自动登录案例</title>
</head>
<body>
<form action="<c:url value='/c1/login'></c:url> " method="post">
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username" ></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="text" name="password"></td>
        </tr>
        <tr>
            <td><input type="checkbox" name="autoLogin" value="ok">自动登录</td>
            <td><input type="checkbox" name="saveName" value="ok">记住用户名</td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit"></td>
        </tr>
    </table>
</form>
<script type="text/javascript">
    onload=function (ev) {
        var s = "${cookie.savename.value}";
        s = decodeURI(s);
        alert(s);

        //将解码后的用户名赋给username的文本框即可
        document.getElementsByName("username")[0].value = s;
    }
</script>
</body>
</html>
