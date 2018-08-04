<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-7-30
  Time: 下午1:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>演示Servlet的API的第一种方式(完全解耦合的方式)</h3>
<form action="${pageContext.request.contextPath}/demo1Action.action" method="post">
    姓名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"/><br>
    <input type="submit" value="注册"/>
</form>
<h3>使用的是SErvletActionContext类方法</h3>
<form action="${pageContext.request.contextPath}/demo2Action.action" method="post">
    姓名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"/><br>
    <input type="submit" value="注册"/>
</form>

</body>
</html>
