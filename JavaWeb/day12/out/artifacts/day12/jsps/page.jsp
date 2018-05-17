<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-17
  Time: 下午8:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" session="true" errorPage="600.jsp" isErrorPage="true" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.setAttribute("skey", "svalue");
    int i = 1 / 0;
%>
<%=session.getAttribute("skey")%>
<%=exception.getMessage()%>
${sessionScope.skey}
</body>
</html>
