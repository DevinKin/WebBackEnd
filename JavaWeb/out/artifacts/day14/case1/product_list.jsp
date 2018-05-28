<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-27
  Time: 上午10:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>案例1-展示所有商品</title>
</head>
<body>
<table border="1" align="center" width="88%">
    <tr>
        <td colspan="7">
            <form action="<c:url value='/FindProductByConditionsServlet'></c:url> " method="post">
                商品名称：<input name="pname" type="text"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                关键词：<input name="keyword" type="text"/>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="submit" value="搜索">
            </form>
        </td>
        <td colspan="1" align="right">
            <input type="button" value="删除选中" onclick="delChecked()">
        </td>
    </tr>
    <tr>
        <th><input type="checkbox" onclick="checkAll(this)">全选</th>
        <th>pid</th>
        <th>商品图片</th>
        <th>商品名称</th>
        <th>市场价</th>
        <th>商城价</th>
        <th>商品描述</th>
        <th>操作</th>
    </tr>
    <form action="<c:url value='/DeleteCheckedProductServlet'></c:url>" method="post" id="formId">
        <c:forEach var="product" items="${plist}">
            <tr>
                <td width="3%"><input type="checkbox" name="pid" value="${product.pid}"></td>
                <td width="8%">${product.pid}</td>
                <td width="8%"><img src="<c:url value='${product.pimage}'></c:url> " alt="商品图片" width="80px"></td>
                <td width="8%">${product.pname}</td>
                <td width="8%">${product.market_price}</td>
                <td width="8%">${product.shop_price}</td>
                <td>${product.pdec}</td>
                <td width="8%">
                    <a href="<c:url value='/GetProductByIdServlet?pid=${product.pid}'></c:url>">修改</a>
                    |
                    <a href="javascript:void(0)" onclick="deleteP('${product.pid}')">删除</a>
                </td>
            </tr>
        </c:forEach>
    </form>

    <script type="text/javascript">
        //删除商品
        function deleteP(obj) {
            if (confirm("你是否要删除该商品?")) {
                location.href = "<c:url value='/DeleteProductByIdServlet?pid=" + obj + "'></c:url>";
            }
        }

        //全选全部选
        function checkAll(obj) {
            //获取所有的复选框
            var arr = document.getElementsByName("pid");
            //遍历数组,修改选中状态
            for (var i = 0; i < arr.length; i++) {
                arr[i].checked = obj.checked;
            }
        }

        //删除选中
        function delChecked() {
            //提交表单
            document.getElementById("formId").submit();
        }
    </script>
</table>
</body>
</html>
