<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-17
  Time: 下午7:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <a href="/jsps/page.jsp">page指令</a><br>
  <a href="/jsps/include.jsp">include指令</a><br>
  <a href="/jsps/pagecontext.jsp">PageContext作用</a><br>
  <a href="/jsps/actionforward.jsp">jsp动作标签-foraward</a><br>
  <a href="/jsps/include.jsp">jsp动作标签-include</a><br>
  <hr>
  <hr>
  <a href="/el/demo1.jsp">el-获取简单数据</a><br>
  <a href="/el/demo2.jsp">el-获取复杂数据</a><br>
  <hr>
  <a href="/el/demo3.jsp">el-javabean导航</a><br>
  <hr>
  <a href="/el/demo4.jsp">el-执行运算</a><br>
  <hr>
  <a href="/el/demo5.jsp?username=tom&password=123&hobby=sleep">el-和参数相关的内置对象</a><br>
  <hr>
  <a href="/el/demo6.jsp">el-和请求头相关的内置对象</a><br>
  <hr>
  <a href="/el/demo7.jsp">el-与全局初始化参数相关的内置对象</a><br>
  <hr>
  <a href="/el/demo8.jsp">el-与cookie相关的内置对象</a><br>
  <hr>
  <a href="${pageContext.request.contextPath}/el/demo9.jsp">el-与pageContext相关的内置对象</a><br>
  <hr>
  <a href="${pageContext.request.contextPath}/jstl/if.jsp">jstl-core下if判断标签的使用</a>
  <hr>
  <a href="${pageContext.request.contextPath}/jstl/for1.jsp">jstl-core下for循环标签的使用</a>
  <hr>
  <a href="${pageContext.request.contextPath}/jstl/for2.jsp">jstl-core下for循环标签的高级使用</a>
  <hr>
  <a href="${pageContext.request.contextPath}/jstl/choose.jsp">jstl-core下choose循环标签的使用</a>
  <hr>
  <a href="${pageContext.request.contextPath}/jstl/fn.jsp">jstl-fn函数库的使用</a>
  <hr>
  <a href="<c:url value='/FindAllServlet'></c:url>">案例1</a>
  <hr>
  <a href="<c:url value='/pro2/index.jsp'></c:url>">案例2</a>
  </body>
</html>
