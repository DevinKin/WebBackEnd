<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-7-30
  Time: 下午10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>使用EL表达式获取值</h3>
${requestScope.msg}<br>
${sessionScope.msg}<br>
${applicationScope.msg}<br>

</body>
</html>
