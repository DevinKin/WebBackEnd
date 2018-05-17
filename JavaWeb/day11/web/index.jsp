<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-16
  Time: 下午7:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  hello jsp!你好
  <%
      int i = 3;
      System.out.println(i);
  %>
  <%=i %>
  <%
    out.print("我很好");
  %>
  <%=k%>
  <%!
    int k = 4;
  %>
  <%=k%>
  </body>
</html>
