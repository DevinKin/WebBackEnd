<%@ page import="cn.devinkin.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午2:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>javaBean导航</title>
</head>
<body>
<%
    User user = new User();
    user.setId("001");
    user.setName("tom");
    user.setPassword("123");
    request.setAttribute("user", user);
%>
获取javabean的id值：<br>
老方式：<%=((User)request.getAttribute("user")).getId()%><br>
EL方式：${user.id}<br>
错误演示：${user.username}
</body>
</html>
