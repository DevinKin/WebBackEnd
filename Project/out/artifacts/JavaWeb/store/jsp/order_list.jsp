<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>会员登录</title>
    <link rel="stylesheet" href="<c:url value='/store'></c:url>/css/bootstrap.min.css" type="text/css"/>
    <script src="<c:url value='/store'></c:url>/js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="<c:url value='/store'></c:url>/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- 引入自定义css文件 style.css -->
    <link rel="stylesheet" href="<c:url value='/store'></c:url>/css/style.css" type="text/css"/>

    <style>
        body {
            margin-top: 20px;
            margin: 0 auto;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>

<jsp:include page="head.jsp"></jsp:include>

</nav>

<div class="container">
    <div class="row">

        <div style="margin:0 auto; margin-top:10px;width:950px;">
            <h2>我的订单</h2>
            <table class="table table-bordered">
                <tbody>
                <c:forEach items="${pb.list}" var="order">
                    <tr class="success">
                        <th colspan="4">订单编号:${order.oid}</th>
                        <th>订单状态：
                            <c:if test="${empty order.state}">空</c:if>
                            <c:choose>
                                <c:when test="${order.state == 0}"><a href="<c:url value='/order?method=getOrderByOid&oid=${order.oid}'></c:url> ">待付款</a> </c:when>
                                <c:when test="${order.state == 1}"><font color="#9acd32">已付款</font> </c:when>
                                <c:when test="${order.state == 2}"><font color="green"> <a href="<c:url value='/order?method=updateOrderState&oid=${order.oid}&state=3'></c:url>">确认收货</a> </font> </c:when>
                                <c:when test="${order.state == 3}"><font color="grey">已收货</font> </c:when>
                            </c:choose>
                        </th>
                    </tr>
                    <tr class="warning">
                        <td>图片</td>
                        <td>商品</td>
                        <td>价格</td>
                        <td>数量</td>
                        <td>小计</td>
                    </tr>
                    <c:forEach var="orderItem" items="${order.orderItemList}">
                        <tr class="active">
                            <td width="60" width="40%">
                                <input type="hidden" name="id" value="22">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${orderItem.product.pid}'></c:url> ">
                                    <img src="<c:url value='/store/${orderItem.product.pimage}'></c:url>" width="70"
                                         height="60">
                                </a>
                            </td>
                            <td width="30%">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${cartItem.product.pid}'></c:url> "> ${orderItem.product.pname}</a>
                            </td>
                            <td width="20%">
                                ￥${orderItem.product.shop_price}
                            </td>
                            <td width="5%">
                                    ${orderItem.count}
                            </td>
                            <td width="20%">
                                <span class="subtotal">￥${orderItem.subtotal}</span>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4"></td>
                        <td colspan="1" class="active">总金额：￥${order.total}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div style="text-align: center;">
        <ul class="pagination">
            <%--判断当前页是否为首页，如果是，则不显示--%>
            <c:if test="${pb.totalPage != 0}">
                <c:if test="${pb.currentPage != 1}">
                    <li>
                        <a href="<c:url value='/order?method=getOrdersByUid&currentPage=${pb.currentPage -1}&cid=${param.cid}'></c:url> "
                           aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
                </c:if>
            </c:if>
            <c:choose>
                <c:when test="${pb.currentPage - 3 <= 0}">
                    <c:set var="beg" value="1"></c:set>
                    <c:set var="end" value="8"></c:set>
                </c:when>
                <c:when test="${pb.currentPage + 4 > pb.totalPage}">
                    <c:set var="beg" value="${pb.totalPage - 7}"></c:set>
                    <c:set var="end" value="${pb.totalPage}"></c:set>
                </c:when>
                <c:otherwise>
                    <c:set var="beg" value="${pb.currentPage - 3}"></c:set>
                    <c:set var="end" value="${pb.currentPage + 4}"></c:set>
                </c:otherwise>
            </c:choose>
            <c:if test="${pb.totalPage < 8}">
                <c:set var="beg" value="1"></c:set>
                <c:set var="end" value="${pb.totalPage}"></c:set>
            </c:if>
            <c:forEach begin="${beg}" end="${end}" var="n">

                <c:if test="${n == pb.currentPage}">
                    <li class="active"><a href="javascipt:void(0)">${n}</a></li>
                </c:if>
                <c:if test="${n != pb.currentPage}">
                    <li>
                        <a href="<c:url value='/order?method=getOrdersByUid&currentPage=${n}'></c:url> ">${n}</a>
                    </li>
                </c:if>
            </c:forEach>
            <%--判断当前页是否为尾页，如果是，则不显示--%>
            <c:if test="${pb.totalPage != 0}">
                <c:if test="${pb.currentPage != pb.totalPage}">
                    <li>
                        <a href="<c:url value='/order?method=getOrdersByUid&currentPage=${pb.currentPage+1}'></c:url> "
                           aria-label="Next">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </c:if>
        </ul>
    </div>
</div>

<div style="margin-top:50px;">
    <img src="<c:url value='/store'></c:url>/image/footer.jpg" width="100%" height="78" alt="我们的优势" title="我们的优势"/>
</div>

<div style="text-align: center;margin-top: 5px;">
    <ul class="list-inline">
        <li><a>关于我们</a></li>
        <li><a>联系我们</a></li>
        <li><a>招贤纳士</a></li>
        <li><a>法律声明</a></li>
        <li><a>友情链接</a></li>
        <li><a target="_blank">支付方式</a></li>
        <li><a target="_blank">配送方式</a></li>
        <li><a>服务声明</a></li>
        <li><a>广告声明</a></li>
    </ul>
</div>
<div style="text-align: center;margin-top: 5px;margin-bottom:20px;">
    Copyright &copy; 2005-2016 传智商城 版权所有
</div>
</body>

</html>