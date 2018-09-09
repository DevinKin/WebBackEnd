<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../base.jsp" %>
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
                    <ul>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        浏览厂家信息
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">厂家号:</td>
                <td class="tableContent">${id}</td>
                <td class="columnTitle">厂家类型:</td>
                <td class="tableContent">
                    <c:if test="${ctype == '货物'}">生产货物</c:if>
                    <c:if test="${ctype == '附件'}">生产附件</c:if>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">厂家全名:</td>
                <td class="tableContent">${fullName}</td>
                <td class="columnTitle">名称缩写:</td>
                <td class="tableContent">${factoryName}</td>
            </tr>
            <tr>
                <td class="columnTitle">联系人:</td>
                <td class="tableContent">${contacts}</td>
                <td class="columnTitle">电话:</td>
                <td class="tableContent">${phone}</td>
            </tr>
            <tr>
                <td class="columnTitle">手机:</td>
                <td class="tableContent">${mobile}</td>
                <td class="columnTitle">传真:</td>
                <td class="tableContent">${fax}</td>
            </tr>
            <tr>
                <td class="columnTitle">地址:</td>
                <td class="tableContent">${address}</td>
                <td class="columnTitle">验货员:</td>
                <td class="tableContent">${inspector}</td>
            </tr>
            <tr>
                <td class="columnTitle">说明:</td>
                <td class="tableContent">${remark}</td>
                <td class="columnTitle">排序号:</td>
                <td class="tableContent">${orderNo}</td>
            </tr>
            <tr>
                <td class="columnTitle">状态:</td>
                <td class="tableContent">
                    <c:if test="${state == 1}">合作</c:if>
                    <c:if test="${state == 2}">停止合作</c:if>
                </td>
            </tr>
        </table>
    </div>


</form>
</body>
</html>

