<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-28
  Time: 下午4:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table border="1" align="center" width="88%">
    <tr>
        <th>pid</th>
        <th>商品图片</th>
        <th>商品名称</th>
        <th>市场价</th>
        <th>商城价</th>
        <th>商品描述</th>
    </tr>
    <c:forEach items="${pb.list}" var="product">
        <tr>
            <td width="8%">${product.pid}</td>
            <td width="8%"><img src="<c:url value='${product.pimage}'></c:url> " alt="商品图片"></td>
            <td width="8%">${product.pname}</td>
            <td width="8%">${product.market_price}</td>
            <td width="8%">${product.shop_price}</td>
            <td width="8%">${product.pdec}</td>
        </tr>
    </c:forEach>
</table>
<center>
    <%--如果是第一页，首页和上一页不展示--%>
    <c:if test="${pb.currentPage != 1}">
        <a href="<c:url value='/ShowProductByPageServlet?currentPage=1'></c:url> ">[首页]</a>
        <a href="<c:url value='/ShowProductByPageServlet?currentPage=${pb.currentPage-1}'></c:url> ">[上一页]</a>
    </c:if>

    <c:choose>
        <c:when test="${pb.totalPage < 9}">
            <c:set var="beg" value="1"></c:set>
            <c:set var="ed" value="${pb.totalPage}"></c:set>
        </c:when>
        <c:otherwise>
            <c:set var="beg" value="${pb.currentPage - 5}"></c:set>
            <c:set var="ed" value="${pb.currentPage + 4}"></c:set>
            <c:if test="${pb.currentPage - 5 < 1}">
                <c:set var="beg" value="1"></c:set>
                <c:set var="ed" value="10"></c:set>
            </c:if>
            <c:if test="${pb.currentPage + 4 > pb.totalPage}">
                <c:set var="ed" value="${pb.totalPage}"></c:set>
                <c:set var="beg" value="${pb.totalPage-10}"></c:set>
            </c:if>
        </c:otherwise>
    </c:choose>
    <%--将所有的页码展示出来--%>
    <c:forEach begin="${beg}" end="${ed}" var="n">
        <%--若是当前页，不可点--%>
        <c:if test="${pb.currentPage == n}">
            [${pb.currentPage}]
        </c:if>
        <c:if test="${pb.currentPage != n}">
            <a href="<c:url value='ShowProductByPageServlet?currentPage=${n}'></c:url> ">[${n}]</a>
        </c:if>
    </c:forEach>
        
        
        
    <c:if test="${pb.currentPage != pb.totalPage}">
        <a href="<c:url value='/ShowProductByPageServlet?currentPage=${pb.currentPage+1}'></c:url> ">[下一页]</a>
        <a href="<c:url value='/ShowProductByPageServlet?currentPage=${pb.totalPage}'></c:url> ">[尾页]</a>
    </c:if>
    第${pb.currentPage}页/共${pb.totalPage}页
</center>

</body>
</html>
