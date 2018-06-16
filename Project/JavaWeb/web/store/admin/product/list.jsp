<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value='/store'></c:url>/css/Style1.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/store'></c:url>/css/bootstrap.min.css" type="text/css"/>
    <script language="javascript" src="<c:url value='/store'></c:url>/js/public.js"></script>
    <script type="text/javascript">
        function addProduct() {
            window.location.href = "<c:url value='/adminProduct?method=addProductUI'></c:url>";
        }
    </script>
</HEAD>
<body>
<br>
<form id="Form1" name="Form1" action="<c:url value='/store'></c:url>/user/list.jsp" method="post">
    <table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
        <TBODY>
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3">
                <strong>商品列表</strong>
            </TD>
        </tr>
        <tr>
            <td class="ta_01" align="right">
                <button type="button" id="add" name="add" value="添加商品" class="button_add" onclick="addProduct()">
                    &#28155;&#21152;
                </button>

            </td>
        </tr>
        <tr>
            <td class="ta_01" align="center" bgColor="#f5fafe">
                <table cellspacing="0" cellpadding="1" rules="all"
                       bordercolor="gray" border="1" id="DataGrid1"
                       style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
                    <tr
                            style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

                        <td align="center" width="18%">
                            序号
                        </td>
                        <td align="center" width="17%">
                            商品图片
                        </td>
                        <td align="center" width="17%">
                            商品名称
                        </td>
                        <td align="center" width="17%">
                            商品价格
                        </td>
                        <td align="center" width="17%">
                            是否热门
                        </td>
                        <td width="7%" align="center">
                            编辑
                        </td>
                        <td width="7%" align="center">
                            删除
                        </td>
                    </tr>
                    <c:forEach items="${pb.list }" var="p" varStatus="vs">
                        <tr onmouseover="this.style.backgroundColor = 'white'"
                            onmouseout="this.style.backgroundColor = '#F5FAFE';">
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="18%">
                                    ${vs.count }
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${p.pid}'></c:url> ">
                                    <img width="40" height="45" src="<c:url value='/store/${p.pimage}'></c:url> ">
                                </a>
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                <a href="<c:url value='/product?method=getProductByPid&pid=${p.pid}'></c:url> ">
                                        ${p.pname }
                                </a>
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                    ${p.shop_price }
                            </td>
                            <td style="CURSOR: hand; HEIGHT: 22px" align="center"
                                width="17%">
                                <c:if test="${p.is_hot==1 }">是</c:if>
                                <c:if test="${p.is_hot!=1 }">否</c:if>
                            </td>
                            <td align="center" style="HEIGHT: 22px">
                                <a href="<c:url value='/adminProduct?method=editProductUI&pid=${p.pid}'></c:url> ">
                                    <img src="<c:url value='/store/images/i_edit.gif'></c:url>" border="0"
                                         style="CURSOR: hand">
                                </a>
                            </td>

                            <c:if test="${p.pflag == 1}">
                                <td align="center" style="HEIGHT: 22px">
                                    <a href="<c:url value='/adminProduct?method=unShelveProduct&pid=${p.pid}'></c:url> ">
                                        <img src="<c:url value='/store/image/gif004.gif'></c:url>" width="16"
                                             height="16"
                                             border="0" style="CURSOR: hand">
                                    </a>
                                </td>
                            </c:if>
                            <c:if test="${p.pflag == 0}">
                                <td align="center" style="HEIGHT: 22px">
                                    <a href="<c:url value='/adminProduct?method=shelveProduct&pid=${p.pid}'></c:url> ">
                                        <img src="<c:url value='/store/image/gif005.gif'></c:url>" width="16"
                                             height="16"
                                             border="0" style="CURSOR: hand">
                                    </a>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
        </TBODY>
    </table>
</form>
<!--分页 -->
<div style="width:380px;margin:0 auto;margin-top:50px;">
    <ul class="pagination" style="text-align:center; margin-top:10px;">
        <%--判断当前页是否为首页，如果是，则不显示--%>
        <c:if test="${pb.totalPage != 0 }">
            <c:if test="${pb.currentPage != 1}">
                <li>
                    <a href="<c:url value='/adminProduct?method=findByPage&currentPage=${pb.currentPage -1}&cid=${param.cid}'></c:url> "
                       aria-label="Previous">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
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
                    <a href="<c:url value='/adminProduct?method=findByPage&currentPage=${n}&cid=${param.cid}'></c:url> ">${n}</a>
                </li>
            </c:if>
        </c:forEach>
        <%--判断当前页是否为尾页，如果是，则不显示--%>
        <c:if test="${pb.totalPage != 0}">
            <c:if test="${pb.currentPage != pb.totalPage}">
                <li>
                    <a href="<c:url value='/adminProduct?method=findByPage&currentPage=${pb.currentPage +1}&cid=${param.cid}'></c:url> "
                       aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </c:if>
    </ul>
</div>
<!-- 分页结束=======================        -->
</body>
</HTML>

