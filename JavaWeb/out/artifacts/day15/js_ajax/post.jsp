<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-28
  Time: 下午9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<input type="button" value="点我" onclick="btnOnclick()">

<script type="text/javascript" src="getXmlHttpRequest.js"></script>
<script type="text/javascript">
    function btnOnclick() {
        //获取核心对象
        xmlHttpRequest = getXmlHttpRequest();
        //设置回调函数
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                alert(xmlHttpRequest.responseText);
            }
        }
        xmlHttpRequest.open("post", "<c:url value='/AjaxServlet2'></c:url>");
        xmlHttpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded")
        xmlHttpRequest.send("username=张三");
    }
</script>
</body>
</html>
