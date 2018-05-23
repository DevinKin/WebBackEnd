<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-18
  Time: 下午12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>操作其他域对象</title>
</head>
<body>
<%
    pageContext.setAttribute("rkey", "rvalue", PageContext.REQUEST_SCOPE);
    session.setAttribute("rkey", "svalue");
%>

<%--<%=request.getAttribute("rkey")--%>
<%=pageContext.getAttribute("rkey", PageContext.REQUEST_SCOPE)%>
<hr>
获取session的rkey：<%=session.getAttribute("rkey")%>
<hr>
便捷查找：<%=pageContext.findAttribute("rkey")%>

</body>
</html>
