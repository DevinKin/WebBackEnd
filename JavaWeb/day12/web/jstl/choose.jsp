<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午6:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>choose标签的使用</title>
</head>
<body>
<c:set value="3" var="day" ></c:set>
<c:choose>
    <c:when test="${day == 1}">
        周一
    </c:when>
    <c:when test="${day == 2}">
        周二
    </c:when>
    <c:when test="${day == 3}">
        周三
    </c:when>
    <c:otherwise>
        放假
    </c:otherwise>
</c:choose>
</body>
</html>
