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

        .container .row div {
            /* position:relative;
 float:left; */
        }

        font {
            color: #3164af;
            font-size: 18px;
            font-weight: normal;
            padding: 0 10px;
        }
    </style>
</head>

<body>

<jsp:include page="head.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <c:if test="${empty cart.cartItemMap}">
            <h1>购物车空空如也，逛逛吧</h1>
        </c:if>
        <c:if test="${not empty cart.cartItemMap}">
            <div style="margin:0 auto; margin-top:10px;width:950px;">
                <strong style="font-size:16px;margin:5px 0;">订单详情</strong>
                <table class="table table-bordered">
                    <tbody>
                    <tr class="warning">
                        <th><input type="checkbox" id="allchecked"/></th>
                        <th>图片</th>
                        <th>商品</th>
                        <th>价格</th>
                        <th>数量</th>
                        <th>小计</th>
                        <th>操作</th>
                    </tr>
                    <c:set var="k" value="0"></c:set>
                    <c:forEach items="${cart.cartItems}" var="cartItem" varStatus="vs">
                        <tr class="active">
                            <td><input type="checkbox" id="cbid${vs.index}" value="${cartItem.product.pid}"/></td>
                            <td width="60" width="40%">
                                <input type="hidden" name="id" value="22">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${cartItem.product.pid}'></c:url> ">
                                    <img src="<c:url value='/store/${cartItem.product.pimage}'></c:url>" width="70"
                                         height="60">
                                </a>
                            </td>
                            <td width="30%">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${cartItem.product.pid}'></c:url> ">${cartItem.product.pname}</a>
                            </td>
                            <td width="20%">
                                ￥${cartItem.product.shop_price}
                            </td>
                            <td width="10%">
                                <input type="text" name="quantity" value="${cartItem.count}" maxlength="4" size="10"
                                       readonly="readonly">
                            </td>
                            <td width="15%">
                                <span class="subtotal">${cartItem.subtotal}</span>
                            </td>
                            <td>
                                <a href="javascript:void(0);" class="delete"
                                   onclick="removeFromCart('${cartItem.product.pid}')">删除</a>
                            </td>
                        </tr>
                        <c:set var="k" value="${k + 1}"></c:set>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <div style="margin-right:130px;">
        <div style="text-align:right;">
            <em style="color:#ff6600;">
                登录后确认是否享有优惠&nbsp;&nbsp;
            </em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong
                style="color:#ff6600;">￥${cart.total}元</strong>
        </div>
        <div style="text-align:right;margin-top:10px;margin-bottom:10px;">
            <a href="javascript:void(0);" id="clear" class="clear" onclick="clearCart()">清空购物车</a>
            <a href="<c:url value='/order?method=addOrder'></c:url> ">
                <input type="submit" width="100" value="提交订单" name="submit" border="0" style="background: url('<c:url
                        value='/store'></c:url>/images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
                        height:35px;width:100px;color:white;">
            </a>
        </div>
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
<script type="text/javascript">
    function removeFromCart(pid) {
        if (confirm("你真的要删除吗?")) {
            location.href = "${pageContext.request.contextPath}/cart?method=remove&pid=" + pid;
        }
    }

    function clearCart() {
        if (confirm("你真的要清空购物车吗?")) {
            location.href = "${pageContext.request.contextPath}/cart?method=clear";
        }
    }

    $(function () {
        for (var i = 0; i < "${k}"; i++) {
            //获取对应checkbox的id
            $cb = $("#cbid" + i.toString());
            //获取对应checkbox的value(pid)
            var params = "&pid=" + $cb.val();
            var url = "<c:url value='/cart?method=modifyState'></c:url>";
            //将请求url和参数传入对应checkbox的value属性中
            $cb.val(url + params);
            //为对应checkbox派发onchange事件
            $cb.change(function () {
                //发送ajax请求，修改购物车条目的selected变量的状态
                $.get(this.value, function () {

                });
            });
        }
        $("#allchecked").change(function () {
            //获取全选中状态
            var allflag = $("#allchecked").prop("checked");
            $cblist = $("td input[type='checkbox']");
            $cblist.each(function () {
                //获取单独选中状态
                var cbflag = $(this).prop("checked");
                //只有在全选中和单独选中框状态不一致的时候才发送ajax请求
                if (allflag != cbflag) {
                    $.get(this.value, function () {

                    });
                }
                //设置单独选中状态
                $(this).prop("checked", allflag);
            });
        });
    });
</script>
</html>