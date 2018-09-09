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
                                         onclick="formSubmit('financeAction_insert','_self');this.blur();">保存</a></li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        新增财务报运单
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">制单日期:</td>
                <td class="tableContent">
                    <input type="text" name="inputDate" value=""
                           onclick="WdatePicker({el:this, isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
                </td>
                <td class="columnTitle">制单人:</td>
                <td class="tableContent"><input type="text" name="inputBy" value=""/></td>
            </tr>
        </table>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        发票列表
    </div>

    <div>


        <div class="eXtremeTable">
            <table id="ec_table" class="tableRegion" width="98%">
                <thead>
                <tr>
                    <td class="tableHeader"></td>
                    <td class="tableHeader">序号</td>
                    <td class="tableHeader">发票ID</td>
                    <td class="tableHeader">发票号</td>
                    <td class="tableHeader">提单号</td>
                    <td class="tableHeader">贸易条款</td>
                    <td class="tableHeader">状态</td>
                </tr>
                </thead>
                <tbody class="tableBody">

                <c:forEach items="${invoiceList}" var="o" varStatus="status">
                    <tr align="left" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                        <td><input type="radio" name="id" value="${o.id}"/></td>
                        <td>${status.index+1}</td>
                        <td><a href="invoiceAction_toview?id=${o.id}">${o.id}</a></td>
                        <td>${o.scNo}</td>
                        <td>${o.blNo}</td>
                        <td>${o.tradeTerms}</td>
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

