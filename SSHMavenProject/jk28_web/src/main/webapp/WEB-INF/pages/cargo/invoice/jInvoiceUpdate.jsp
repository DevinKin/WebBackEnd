<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>
</head>

<body>
<form name="icform" method="post">
    <input type="hidden" name="id" value="${id}"/>
    <div id="menubar">
        <div id="middleMenubar">
            <div id="innerMenubar">
                <div id="navMenubar">
                    <ul>
                        <li id="save"><a href="#"
                                         onclick="formSubmit('invoiceAction_update','_self');this.blur();">保存</a></li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        修改发票
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">发票号:</td>
                <td class="tableContent"><input type="text" name="scNo" value="${scNo}"/></td>
                <td class="columnTitle">提单号：</td>
                <td class="tableContent"><input type="text" name="blNo" value="${blNo}"/></td>
            </tr>
            <tr>
                <td class="columnTitle">贸易条款:</td>
                <td class="tableContent"><input type="text" name="tradeTerms" value="${tradeTerms}"/></td>
            </tr>
        </table>
    </div>


</form>
</body>
</html>

