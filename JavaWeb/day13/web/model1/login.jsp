<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--接受值--%>
<jsp:useBean id="u" class="cn.devinkin.domain.User"></jsp:useBean>
<jsp:setProperty name="u" property="username"/>
<jsp:setProperty name="u" property="password"/>

<%--打印值--%>
<jsp:getProperty name="u" property="username"/>
<jsp:getProperty name="u" property="password"/>
</body>
</html>
