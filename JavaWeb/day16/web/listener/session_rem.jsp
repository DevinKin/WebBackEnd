<%@ page import="cn.devinkin.listener.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-30
  Time: 下午8:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HttpSessionBindingListener</title>
</head>
<body>
将user对象添加到session中
<%
    session.removeAttribute("u");
%>

</body>
</html>
