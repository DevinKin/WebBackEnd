<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午5:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>ForEach标签的高级用法</title>
</head>
<body>
<%
    List list = new ArrayList();
    list.add(11);
    list.add(22);
    list.add(33);
    request.setAttribute("list", list);

    Set set = new HashSet();
    set.add("11");
    set.add("22");
    set.add("33");
    request.setAttribute("set", set);

    Map m = new HashMap();
    m.put("username", "tom");
    m.put("age", "18");
    request.setAttribute("map", m);
%>

<c:forEach var="n" items="${list}">
    ${n}<br>
</c:forEach>
<hr>
<c:forEach var="n" items="${set}" varStatus="vs">
    ${n}-----${vs.count}<br>
</c:forEach>
<hr>
<c:forEach var="n" items="${map}" varStatus="vs">
    ${n.key}:${n.value}-----${vs.count}<br>
</c:forEach>

</body>

</html>
