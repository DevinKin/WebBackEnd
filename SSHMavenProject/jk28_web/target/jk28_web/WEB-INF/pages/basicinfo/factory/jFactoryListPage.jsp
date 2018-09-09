<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../baselist.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
</head>

<body>
<form name="icform" method="post">

    <div id="menubar">
        <div id="middleMenubar">
            <div id="innerMenubar">
                <div id="navMenubar">
                    <%--<ul>--%>
                    <%--<li id="view"><a href="#"--%>
                    <%--onclick="formSubmit('factoryAction_toview','_self');this.blur();">查看</a></li>--%>
                    <%--<li id="new"><a href="#"--%>
                    <%--onclick="formSubmit('factoryAction_tocreate','_self');this.blur();">新增</a></li>--%>
                    <%--<li id="update"><a href="#" onclick="formSubmit('factoryAction_toupdate','_self');this.blur();">修改</a>--%>
                    <%--</li>--%>
                    <%--<li id="delete"><a href="#"--%>
                    <%--onclick="formSubmit('factoryAction_delete','_self');this.blur();">删除</a></li>--%>
                    <%--<li id="new"><a href="#"--%>
                    <%--onclick="formSubmit('factoryAction_cooperate','_self');this.blur();">合作</a></li>--%>
                    <%--<li id="new"><a href="#"--%>
                    <%--onclick="formSubmit('factoryAction_cancel','_self');this.blur();">不合作</a></li>--%>
                    <%--</ul>--%>
                    <c:set var="pmodule" value="厂家信息"/>
                    <%@include file="../curdmenu.jsp" %>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        生产厂家列表
    </div>

    <div>


        <div class="eXtremeTable">
            <table id="ec_table" class="tableRegion" width="98%">
                <thead>
                <tr>
                    <td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                    <td class="tableHeader">序号</td>
                    <td class="tableHeader">厂家号</td>
                    <td class="tableHeader">厂家类型</td>
                    <td class="tableHeader">厂家全称</td>
                    <td class="tableHeader">名称缩写</td>
                    <td class="tableHeader">联系人</td>
                    <td class="tableHeader">电话</td>
                    <td class="tableHeader">手机</td>
                    <td class="tableHeader">传真</td>
                    <td class="tableHeader">地址</td>
                    <td class="tableHeader">验货员</td>
                    <td class="tableHeader">说明</td>
                    <td class="tableHeader">排序号</td>
                    <td class="tableHeader">状态</td>
                </tr>
                </thead>
                <tbody class="tableBody">
                ${links}

                <c:forEach items="${results}" var="o" varStatus="status">
                    <tr align="left" class="odd" onmouseover="this.className='highlight'"
                        onmouseout="this.className='odd'">
                        <td><input type="checkbox" name="id" value="${o.id}"/></td>
                        <td>${status.index+1}</td>
                        <td><a href="factoryAction_toview?id=${o.id}">${o.id}</a></td>
                        <td>
                            <c:if test="${o.ctype == '货物'}">生产货物</c:if>
                            <c:if test="${o.ctype == '附件'}">生产附件</c:if>
                        </td>
                        <td>${o.fullName}</td>
                        <td>${o.factoryName}</td>
                        <td>${o.contacts}</td>
                        <td>${o.phone}</td>
                        <td>${o.mobile}</td>
                        <td>${o.fax}</td>
                        <td>${o.address}</td>
                        <td>${o.inspector}</td>
                        <td>${o.remark}</td>
                        <td>${o.orderNo}</td>
                        <td>
                            <c:if test="${o.state == 1}">合作</c:if>
                            <c:if test="${o.state == 2}">停止合作</c:if>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
</form>
</body>
</html>

