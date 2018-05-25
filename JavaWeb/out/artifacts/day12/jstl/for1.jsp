<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午4:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>c:for的使用</title>
</head>
<body>
<c:forEach begin="1" end="10" step="1" var="i">
    ${i}<br>
</c:forEach>
<c:forEach begin="1" end="20" step="2" var="i" varStatus="vs">
    ${i}-----${vs.count}-----${vs.current}<br>
</c:forEach>
</body>
</html>
