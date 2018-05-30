<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-28
  Time: 下午9:20
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
        //获取核心对象
        xmlHttpRequest = getXmlHttpRequest();
        //设置回调函数
        xmlHttpRequest.onreadystatechange = function () {
            if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                alert(xmlHttpRequest.responseText);
            }
        }
        xmlHttpRequest.open("get", "<c:url value='/AjaxServlet2?username=张三'></c:url>");
        xmlHttpRequest.send();
    }
</script>
</html>
