<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-22
  Time: 下午11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取简单数据</title>
</head>
<body>
    <%
        request.setAttribute("rkey", "rvalue");
        session.setAttribute("skey", "svalue");
        application.setAttribute("akey", "avalue");
    %>
    获取request中的数据：<br>
    老方式：<%=request.getAttribute("rkey")%><br>
    EL方式：${requestScope.rkey}<br>

    获取request中的数据：<br>
    老方式：<%=session.getAttribute("rkey")%><br>
    EL方式：${sessionScope.skey}<br>

    获取request中的数据：<br>
    老方式：<%=application.getAttribute("akey")%><br>
    EL方式：${applicationScope.akey}<br>
    <hr>
    获取失败老方式：<%=application.getAttribute("aakey")%><br>
    获取失败el方式：${applicationScope.aakey}
    <hr>
    便捷获取：${skey},${rkey},${akey},${aakey}
</body>
</html>
