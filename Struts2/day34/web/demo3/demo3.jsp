<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-8-4
  Time: 上午8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>编写表单</h3>
<form action="" method="post">
    性别<input type="radio" name="sex"/>男<input type="radio" name="sex"/>女
</form>

<h3>使用UI标签方式</h3>
<s:form action="" method="post">
    <%--性别<s:radio name="sex" list="{'男','女'}"/>--%>
    性别：<s:radio name="sex" list="#{'1':'男','2':'女'}"/>
</s:form>

<s:property value="%{'aaa'}"/>

</body>
</html>
