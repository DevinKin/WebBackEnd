<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-28
  Time: 下午7:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<a href="<c:url value='/js_ajax/hello.jsp'></c:url> ">入门-原生ajax</a><br>
<a href="<c:url value='/js_ajax/get.jsp'></c:url> ">ajax-get请求</a><br>
<a href="<c:url value='/js_ajax/post.jsp'></c:url> ">ajax-post请求</a><br>
<a href="<c:url value='/case1/case1.jsp'></c:url> ">案例1-用户名是否被注册</a><br>
<a href="<c:url value='/jquery_ajax/jq_ajax.jsp'></c:url> ">jquery-ajax四种方法</a><br>
<a href="<c:url value='/case2/case2.jsp'></c:url> ">案例2-用户名是否被注册</a><br>
<a href="<c:url value='/case3/case3.jsp'></c:url> ">案例3-模仿百度搜索</a><br>
<a href="<c:url value='/case4/case4.jsp'></c:url> ">案例4-省市联动</a><br>
</body>
</html>
