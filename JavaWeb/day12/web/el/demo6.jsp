<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午3:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>和请求头相关的el内置对象</title>
</head>
<body>
${header}
<hr>
${headerValues}
<hr>
referer:${header.referer}<br>
user-agent:${headerValues["user-agent"][0]}
</body>
</html>
