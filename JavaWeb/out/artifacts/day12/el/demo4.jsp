<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="cn.devinkin.domain.User" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午2:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>执行运算</title>
</head>
<body>
<%
    request.setAttribute("i", 3);
    request.setAttribute("j", 4);
    request.setAttribute("q", "12");
    request.setAttribute("k", "k");

    List list = null;
    request.setAttribute("list", list);
    List list2 = new ArrayList();
    list2.add("22");
    request.setAttribute("list2", list2);

    User user = null;
    request.setAttribute("user", user);
    User user2 = new User();
    request.setAttribute("user2", user2);
%>
${i+j}<br/>
${i+q}<br/>
${q+q}<br/>
<%--${i+k}<br/>--%>
<hr>
域中list的长度是否为0：${empty list}<br/>
域中list2的长度是否为0：${empty list2}<br/>
<hr>
域中的bean是否为空：${empty user}<br>
域中的bean是否为空：${empty user2}<br>
<hr>
${3>4?"yes":"no"}<br>
${i==3?"yes":"no"}<br>
</body>
</html>
