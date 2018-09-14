<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.4.4.min.js"></script>
    <title>查询商品列表</title>
</head>
<body>
<%--<form action="${pageContext.request.contextPath }/search.action" method="post">--%>
<%--<form action="${pageContext.request.contextPath }/delAll.action" method="post">--%>
<input type="button" value="sendJson" onclick="sendJson()">
<form action="${pageContext.request.contextPath }/items/updateAll.action" method="post">
    查询条件：
    <table width="100%" border=1>
        <tr>
            <%-- 如果Controller中接收的是Vo,那么页面上的input的name属性值要等于vo的属性.属性--%>
            <td>商品名称:<input type="text" name="items.name"/></td>
            <td>商品价格:<input type="text" name="items.price"/></td>
            <%--<td><input type="submit" value="查询"/></td>--%>
            <td><input type="submit" value="批量修改"/></td>
        </tr>
    </table>
    商品列表：
    <table width="100%" border=1>
        <tr>
            <td>商品名称</td>
            <td>商品价格</td>
            <td>生产日期</td>
            <td>商品描述</td>
            <td>操作</td>
        </tr>
        <c:forEach items="${itemList }" var="item" varStatus="s">
        <%--1. 如果批量修改,可以用`List<pojo>`来接收,页面上的input框的name属性=`vo中接收的集合属性名称[list下标].list泛型的属性名称.属性`--%>
            <tr>
                    <%-- name属性要等于vo中接收的属性名 --%>
                <td>
                    <input type="checkbox" name="ids" value="${item.id}"/>
                    <input type="hidden" name="itemsList[${s.index}].id" value="${item.id}">
                </td>
                <td>
                        ${item.name }
                    <input type="hidden" name="itemsList[${s.index}].name" value="${item.name}">
                </td>
                <td>
                        ${item.price }
                    <input type="hidden" name="itemsList[${s.index}].price" value="${item.price}">
                </td>
                <td>
                    <fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    <<input type="hidden" name="itemsList[${s.index}].createtime"
                            value="<fmt:formatDate value="${item.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                </td>
                <td>
                        ${item.detail }
                    <input type="hidden" name="itemsList[${s.index}].detail" value="${item.detail}">
                </td>

                <td><a href="${pageContext.request.contextPath }/items/itemEdit/${item.id}/张三">修改</a></td>

            </tr>
        </c:forEach>

    </table>
</form>
<script type="text/javascript">
    function sendJson() {
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/items/sendJson.action",
            contentType:"application/json;charset=utf-8",
            data:'{"name":"测试商品","price":99.9}',
            success:function (data) {
                alert(data);
            }
        });
    }
</script>
</body>

</html>