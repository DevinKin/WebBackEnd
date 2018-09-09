<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">

    <div id="menubar">
        <div id="middleMenubar">
            <div id="innerMenubar">
                <div id="navMenubar">
                    <ul>
                        <li id="save"><a href="#"
                                         onclick="formSubmit('invoiceAction_insert','_self');this.blur();">保存</a></li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        新增发票
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">发票号:</td>
                <td class="tableContent"><input type="text" name="scNo" value=""/></td>
                <td class="columnTitle">提单号:</td>
                <td class="tableContent"><input type="text" name="blNo" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">贸易条款</td>
                <td class="tableContent"><input type="text" name="tradeTerms" value=""/></td>
            </tr>
        </table>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        委托单列表
    </div>

    <div>
        <div class="eXtremeTable">
            <table id="ec_table" class="tableRegion" width="98%">
                <thead>
                <tr>
                    <td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
                    <td class="tableHeader"></td>
                    <td class="tableHeader">委托单号</td>
                    <td class="tableHeader">运输方式</td>
                    <td class="tableHeader">货主</td>
                    <td class="tableHeader">提单抬头</td>
                    <td class="tableHeader">正本通知人</td>
                    <td class="tableHeader">信用证</td>
                    <td class="tableHeader">装运港</td>
                    <td class="tableHeader">转船港</td>
                    <td class="tableHeader">卸货港</td>
                    <td class="tableHeader">装期</td>
                    <td class="tableHeader">效期</td>
                    <td class="tableHeader">是否分批</td>
                    <td class="tableHeader">是否转船</td>
                    <td class="tableHeader">份数</td>
                    <td class="tableHeader">扼要说明</td>
                    <td class="tableHeader">运输要求</td>
                    <td class="tableHeader">运费说明</td>
                    <td class="tableHeader">复核人</td>
                    <td class="tableHeader">状态</td>
                </tr>
                </thead>
                <tbody class="tableBody">

                <c:forEach items="${shippingOrderList}" var="o" varStatus="status">
                    <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                        <td><input type="radio" name="id" value="${o.id}"/></td>
                        <td>${status.index+1}</td>
                        <td><a href="shippingOrderAction_toview?id=${o.id}">${o.id}</a></td>
                        <td>
                            <c:if test="${o.orderType == 'SEA'}">海运</c:if>
                            <c:if test="${o.orderType == 'AIR'}">空运</c:if>
                        </td>
                        <td>${o.shipper}</td>
                        <td>${o.consignee}</td>
                        <td>${o.notifyParty}</td>
                        <td>${o.lcNo}</td>
                        <td>${o.portOfLoading}</td>
                        <td>${o.portOfTrans}</td>
                        <td>${o.portOfDischarge}</td>
                        <td>
                            <fmt:formatDate value="${o.loadingDate}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${o.limitDate}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            <c:if test="${o.isBatch == 0}">分批</c:if>
                            <c:if test="${o.isBatch == 1}">不分批</c:if>
                        </td>
                        <td>
                            <c:if test="${o.isTrans == 0}">转船</c:if>
                            <c:if test="${o.isTrans == 1}">不转船</c:if>
                        </td>
                        <td>${o.copyNum}</td>
                        <td>${o.remark}</td>
                        <td>${o.specialCondition}</td>
                        <td>${o.freight}</td>
                        <td>${o.checkBy}</td>
                        <td>
                            <c:if test="${o.state == 0}">草稿</c:if>
                            <c:if test="${o.state == 1}">已上报</c:if>
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

