<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-30
  Time: 下午6:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <a href="<c:url value='/listener/request.jsp'></c:url>">request生命周期的测试</a><br>
  <a href="<c:url value='/listener/session.jsp'></c:url>">session生命周期的测试</a><br>
  <a href="<c:url value='/listener/attr_set.jsp'></c:url>">ServletContextListener-添加属性</a><br>
  <a href="<c:url value='/listener/attr_rep.jsp'></c:url>">ServletContextListener-替换属性</a><br>
  <a href="<c:url value='/listener/attr_rem.jsp'></c:url>">ServletContextListener-删除属性</a><br>
  <a href="<c:url value='/listener/session_set.jsp'></c:url>">HttpSessionBindingListener-javabean绑定到session中</a><br>
  <a href="<c:url value='/listener/session_rem.jsp'></c:url>">HttpSessionBindingListener-javabean从session中解绑</a><br>
  <a href="<c:url value='/listener/session_getb.jsp'></c:url>">HttpSessionBindingListener-获取javabean名称(活化)</a><br>
  <a href="<c:url value='/Demo3Servlet'></c:url>">FilterChain执行顺序</a><br>
  <a href="<c:url value='/case1/login.jsp'></c:url>">案例1-用户自动登录</a><br>
  <a href="<c:url value='/e/Demo5Servlet'></c:url>">Dispatcher使用</a><br>
  <a href="<c:url value='/form/form.jsp'></c:url>">案例2-统一编码</a><br>
  </body>
</html>
