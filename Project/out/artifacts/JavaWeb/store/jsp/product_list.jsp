<%@ page import="cn.devinkin.utils.CookieUtils" %>
<%@ page import="cn.devinkin.product.domain.Product" %>
<%@ page import="cn.devinkin.product.dao.ProductDao" %>
<%@ page import="cn.devinkin.product.dao.impl.ProductDaoImpl" %>
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
            width: 100%;
        }

        .carousel-inner .item img {
            width: 100%;
            height: 300px;
        }
    </style>
</head>

<body>

<jsp:include page="head.jsp"></jsp:include>


<div class="row" style="width:1210px;margin:0 auto;">
    <div class="col-md-12">
        <ol class="breadcrumb">
            <li><a href="#">首页</a></li>
        </ol>
    </div>

    <c:forEach items="${pb.list}" var="product">
        <div class="col-md-2">
            <a href="<c:url value='/product?method=getProductByPid&pid=${product.pid}'></c:url> ">
                <img src="<c:url value='/store/${product.pimage}'></c:url>" width="170" height="170"
                     style="display: inline-block;">
            </a>
            <p><a href="<c:url value='/product?method=getProductByPid&pid=${product.pid}'></c:url> "
                  style='color:green'>${product.pname}</a></p>
            <p><font color="#FF0000">商城价：&yen;${product.shop_price}</font></p>
        </div>
    </c:forEach>

</div>

<!--分页 -->
<div style="width:380px;margin:0 auto;margin-top:50px;">
    <ul class="pagination" style="text-align:center; margin-top:10px;">
        <%--判断当前页是否为首页，如果是，则不显示--%>
        <c:if test="${pb.totalPage != 0}">
            <c:if test="${pb.currentPage != 1}">
                <li>
                    <a href="<c:url value='/product?method=findByPage&currentPage=${pb.currentPage -1}&cid=${param.cid}'></c:url> "
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
                    <a href="<c:url value='/product?method=findByPage&currentPage=${n}&cid=${param.cid}'></c:url> ">${n}</a>
                </li>
            </c:if>
        </c:forEach>
        <%--判断当前页是否为尾页，如果是，则不显示--%>
        <c:if test="${pb.totalPage != 0}">
            <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    <a href="<c:url value='/product?method=findByPage&currentPage=${pb.currentPage +1}&cid=${param.cid}'></c:url> "
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </c:if>
    </ul>
</div>
<!-- 分页结束=======================        -->

<!--
       商品浏览记录:
-->
<div style="width:1210px;margin:0 auto; padding: 0 9px;border: 1px solid #ddd;border-top: 2px solid #999;height: 246px;">

    <h4 style="width: 50%;float: left;font: 14px/30px " 微软雅黑 ";">浏览记录</h4>
    <div style="width: 50%;float: right;text-align: right;"><a href="">more</a></div>
    <div style="clear: both;"></div>

    <div style="overflow: hidden;">

        <ul style="list-style: none;">
            <%
                //                1. 获取指定cookie
//                2. 判断cookie是否为空
//                1. 若不为空
//                  1. 获取cookie的值
//                  2. 截取字符串
//                  3. 通过id在数据库中查找对应商品信息
                Cookie cookie = CookieUtils.getCookieByName("ids", request.getCookies());
                if (cookie != null) {
                    String ids = cookie.getValue();
                    String[] idArray = ids.split("-");
                    for (String pid : idArray) {
                        Product product = new ProductDaoImpl().getProductByPid(pid);
                        String piamge = product.getPimage();
                        String qpid = product.getPid();
            %>
            <li style="width: 150px;height: 216;float: left;margin: 0 8px 0 0;padding: 0 18px 15px;text-align: center;">
                <a href="${pageContext.request.contextPath}/product?method=getProductByPid&pid=<%=qpid%>"><img
                        src="${pageContext.request.contextPath}/store/<%=piamge%>" width="130px" height="130px"/></a>
            </li>
            <%
                    }
                }
            %>
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