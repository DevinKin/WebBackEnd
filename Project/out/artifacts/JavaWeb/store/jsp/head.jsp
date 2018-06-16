<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--
时间：2015-12-30
描述：菜单栏
-->
<div class="container-fluid">
    <div class="col-md-4">
        <img src="<c:url value='/store/img/logo2.png'></c:url>"/>
    </div>
    <div class="col-md-5">
        <img src="<c:url value='/store/img/header.png'></c:url>"/>
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${empty user}">
                <li><a href="<c:url value='/user?method=loginUI'></c:url>">登录</a></li>
                <li><a href="<c:url value='/user?method=registUI'></c:url>">注册</a></li>
            </c:if>
            <c:if test="${not empty user}">
                <li>${user.username}：您好</li>
                <li><a href="<c:url value='/user?method=logout'></c:url>">退出</a></li>
                <li><a href="<c:url value='/store/jsp/cart.jsp'></c:url>">购物车</a></li>
                <li><a href="<c:url value='/order?method=getOrdersByUid&currentPage=1'></c:url>">我的订单</a></li>
            </c:if>
        </ol>
    </div>
</div>
<!--
时间：2015-12-30
描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<c:url value='/index?method=index'></c:url> ">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="menuId">
                    <%--<li class="active"><a href="<c:url value='/store/jsp/'></c:url>product_list.jsp">${category.cname}<span class="sr-only">(current)</span></a>--%>
                    <%--</li>--%>
                </ul>
                <form class="navbar-form navbar-right" role="search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>

            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>

<script type="text/javascript">
    $(function () {
        //发送ajax请求
        $.get("${pageContext.request.contextPath}/category?method=findAll",
            function (data) {
                //获取menu的ul标签
                var $ul = $("#menuId");
                //遍历数组
                $(data).each(function () {
                    $ul.append($("<li><a href='<c:url value='/product?method=findByPage&cid=" + this.cid + "&currentPage=1'></c:url> '>"+ this.cname +"</a></li>"));
                });
            }, "json");
    });
</script>