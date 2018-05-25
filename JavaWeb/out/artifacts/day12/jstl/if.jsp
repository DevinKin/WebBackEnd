<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午4:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>jstl</title>
</head>
<body>
<c:if test="${3>4}">
    3大于4
</c:if>
<c:if test="${3<=4}">
    3小于4
</c:if>

</body>
</html>
