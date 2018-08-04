<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-8-2
  Time: 下午10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>从值栈中获取值</h3>
<%--
// vs.push("美美")
// 获取到栈顶的值
<s:property value="[0].top"/>
--%>


<%--
// 栈顶是map集合，通过key获取值
// vs.set("msg", "小风")
<s:property value="[0].top.msg"></s:property>

--%>


<%--
    栈顶放的是User对象
<s:property value="[0].top.username"/>
<s:property value="[0].top.password"/>
[0].top 关键字是可以省略的
<s:property value="username"/>
<s:property value="password"/>
--%>

<%--
    栈顶是包含User对象的Map集合
<s:property value="[0].top.user.username"/>
<s:property value="[0].top.user.password"/>
省略[0].top关键字
<s:property value="user.username"/>
<s:property value="user.password"/>
--%>
<%--<s:property value="user.username"/>--%>
<%--<s:property value="[1].top.user.username"/>--%>


<%--
    栈顶是list
    <s:property value="[0].top[1].username"/>
    <s:property value="ulist[0].username"/>
--%>
<%-- 迭代标签
    属性：
        - value 要迭代的集合，需要从值栈中获取
        - var 迭代过程中，遍历的对象
            - var 若写了，把迭代产生的对象默认压入到context栈中，从context栈取值，加 #号
            - var 不编写，默认把迭代产生的对象压入root栈中
// 编写var的属性
<s:iterator value="ulist" var="user">
    <s:property value="#user.username"/>
    <s:property value="#user.password"/>
</s:iterator>

// 没有编写var的属性
<s:iterator value="ulist">
    <s:property value="[0].top.username"/>
    <s:property value="password"/>
</s:iterator>
--%>

<%-- 从context栈中获取值，加 # 号
<s:property value="#request.msg"/>
<s:property value="#session.msg"/>
<s:property value="#parameters.id"/>
<s:property value="#attr.msg"/>
--%>

<%--
    在JSP页面上可以使用EL和JSTL标签库来取值
    使用装饰者模式
--%>

<%--  在JSP页面上，查看值栈的内部结构--%>
<c:forEach items="${ulist}" var="user">
    ${user.username} --- ${user.password}
</c:forEach>
<s:debug/>

</body>
</html>
