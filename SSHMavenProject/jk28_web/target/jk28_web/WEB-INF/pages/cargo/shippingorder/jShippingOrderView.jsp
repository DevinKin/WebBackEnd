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
        浏览委托单
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">委托单号:</td>
                <td class="tableContent">${id}</td>
                <td class="columnTitle">托运方式:</td>
                <td class="tableContent">
                    <c:if test="${orderType == 'SEA'}">海运</c:if>
                    <c:if test="${orderType == 'AIR'}">空运</c:if>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">货主:</td>
                <td class="tableContent">${shipper}</td>
                <td class="columnTitle">提单抬头:</td>
                <td class="tableContent">${consignee}</td>
            </tr>
            <tr>
                <td class="columnTitle">正本通知人:</td>
                <td class="tableContent">${notifyParty}</td>
                <td class="columnTitle">信用证:</td>
                <td class="tableContent">${lcNo}</td>
            </tr>
            <tr>
                <td class="columnTitle">装运港:</td>
                <td class="tableContent">${portOfLoading}</td>
                <td class="columnTitle">转船港:</td>
                <td class="tableContent">${portOfTrans}</td>
            </tr>
            <tr>
                <td class="columnTitle">卸货港:</td>
                <td class="tableContent">${portOfDischarge}</td>
                <td class="columnTitle">装期:</td>
                <td class="tableContent">
                    <fmt:formatDate value="${loadingDate}" pattern="yyyy-MM-dd"/>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">效期:</td>
                <td class="tableContent">
                    <fmt:formatDate value="${limitDate}" pattern="yyyy-MM-dd"/>
                </td>
                <td class="columnTitle">是否分批</td>
                <td class="tableContent">
                    <c:if test="${isBatch == 0}">分批</c:if>
                    <c:if test="${isBatch == 1}">不分批</c:if>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">是否转船:</td>
                <td class="tableContent">
                    <c:if test="${isTrans == 0}">转船</c:if>
                    <c:if test="${isTrans == 1}">不转船</c:if>
                </td>
                <td class="columnTitle">份数:</td>
                <td class="tableContent">${copyNum}</td>
            </tr>
            <tr>
                <td class="columnTitle">扼要说明:</td>
                <td class="tableContent">${remark}</td>
                <td class="columnTitle">运输要求:</td>
                <td class="tableContent">${specialCondition}</td>
            </tr>
            <tr>
                <td class="columnTitle">运输说明:</td>
                <td class="tableContent">${freight}</td>
                <td class="columnTitle">复核人:</td>
                <td class="tableContent">${checkBy}</td>
            </tr>
            <tr>
                <td class="columnTitle">状态:</td>
                <td class="tableContent">
                    <c:if test="${state == 0}">草稿</c:if>
                    <c:if test="${state == 1}">已上报</c:if>
                </td>
            </tr>
        </table>
    </div>


</form>
</body>
</html>

