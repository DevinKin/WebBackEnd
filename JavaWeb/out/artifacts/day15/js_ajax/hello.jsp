<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-28
  Time: 下午8:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<input type="button" value="点我" onclick="btnOnclick()">
</body>
<script type="text/javascript" src="getXmlHttpRequest.js"></script>
<script type="text/javascript">
    function btnOnclick() {
        /**
         * 1. 创建核心对象XMLHttpRequest
         * 2. 编写回调函数
         * 3. oepn操作，设置请求的方式和请求的路径
         * 4. 发送请求
         */
        xmlhttp = getXmlHttpRequest();

        //编写回调函数
        xmlhttp.onreadystatechange=function () {
            alert(xmlhttp.responseText);
        }
        xmlhttp.open("get","<c:url value='/AjaxServlet1'></c:url> ");
        xmlhttp.send(null);
    }
</script>
</html>
