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
                                         onclick="formSubmit('shippingOrderAction_insert','_self');this.blur();">保存</a>
                        </li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        新增委托单
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">运输方式:</td>
                <td class="tableContent">
                    <select name="orderType">
                        <option value="SEA">海运</option>
                        <option value="AIR">空运</option>
                    </select>
                </td>
                <td class="columnTitle">货主:</td>
                <td class="tableContent"><input type="text" name="shipper" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">提单抬头:</td>
                <td class="tableContent"><input type="text" name="consignee" value=""/></td>
                <td class="columnTitle">正本通知:</td>
                <td class="tableContent"><input type="text" name="notifyParty" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">信用证:</td>
                <td class="tableContent"><input type="text" name="lcNo" value=""/></td>
                <td class="columnTitle">装运港:</td>
                <td class="tableContent"><input type="text" name="portOfLoading" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">转船港:</td>
                <td class="tableContent"><input type="text" name="portOfTrans" value=""/></td>
                <td class="columnTitle">卸货港:</td>
                <td class="tableContent"><input type="text" name="portOfDischarge" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">装期:</td>
                <td class="tableContent">
                    <input type="text" name="loadingDate" value=""
                           onclick="WdatePicker({el:this, isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
                </td>
                <td class="columnTitle">效期:</td>
                <td class="tableContent">
                    <input type="text" name="limitDate" value=""
                           onclick="WdatePicker({el:this, isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">是否分批:</td>
                <td class="tableContent">
                    <select name="isBatch">
                        <option value="0">分批</option>
                        <option value="1" selected>不分批</option>
                    </select>
                </td>
                <td class="columnTitle">是否转船:</td>
                <td class="tableContent">
                    <select name="isTrans">
                        <option value="0">转船</option>
                        <option value="1" selected>不转船</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">份数:</td>
                <td class="tableContent"><input type="text" name="copyNum" value=""/></td>
                <td class="columnTitle">扼要说明</td>
                <td class="tableContent"><input type="text" name="remark" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">运输要求:</td>
                <td class="tableContent"><input type="text" name="specialCondition" value=""/></td>
                <td class="columnTitle">运费说明:</td>
                <td class="tableContent"><input type="text" name="freight" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">复核人:</td>
                <td class="tableContent"><input type="text" name="checkBy" value=""/></td>
            </tr>

        </table>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        装箱单列表
    </div>

    <div>
        <div class="eXtremeTable">
            <table id="ec_table" class="tableRegion" width="98%">
                <thead>
                <tr>
                    <td></td>
                    <td class="tableHeader">序号</td>
                    <td class="tableHeader">编号</td>
                    <td class="tableHeader">卖方</td>
                    <td class="tableHeader">买方</td>
                    <td class="tableHeader">发票号</td>
                    <td class="tableHeader">发票日期</td>
                    <td class="tableHeader">状态</td>
                </tr>
                </thead>
                <tbody class="tableBody">
                <c:forEach items="${packingLists}" var="o" varStatus="status">
                    <tr class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                        <td><input type="radio" name="id" value="${o.id}"/></td>
                        <td>${status.index+1}</td>
                        <td>${o.id}</td>
                        <td>${o.seller}</td>
                        <td>${o.buyer}</td>
                        <td>${o.invoiceNo}</td>
                        <td>
                            <fmt:formatDate value="${o.invoiceDate}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td>
                            <c:if test="${o.state == 0}">草稿</c:if>
                            <c:if test="${o.state == 1}">已装箱</c:if>
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

