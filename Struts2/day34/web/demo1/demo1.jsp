<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-8-1
  Time: 下午12:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 引入Struts2标签库 --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>条件：假如值栈中已经存入值了，在JSP页面上从值栈中取值</h3>
<%-- 1. 县引入Struts2框架提供的标签库
     2. 可以使用提供的标签(掌握重点的标签)
--%>

<%-- 从值栈中获取值的，value中间编写的就是OGNL表达式 --%>
<s:property value="'username'.length()"></s:property>
</body>
</html>
