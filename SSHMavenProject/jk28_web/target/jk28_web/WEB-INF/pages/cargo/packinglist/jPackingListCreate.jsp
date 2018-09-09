<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" src="${ctx}/js/datepicker/WdatePicker.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7"
          crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="${ctx}/multiselect/css/multi-select.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0/jquery.min.js"></script>
    <!-- Bootstrap JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0-alpha/js/bootstrap.min.js"></script>
    <script src="${ctx}/multiselect/js/jquery.multi-select.js"></script>
</head>

<body>
<form name="icform" method="post">

    <div id="menubar">
        <div id="middleMenubar">
            <div id="innerMenubar">
                <div id="navMenubar">
                    <ul>
                        <li id="save"><a href="#" onclick="formSubmit('packingListAction_insert','_self');this.blur();">保存</a>
                        </li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        新增装箱单
    </div>


    <div>
        <table class="commonTable" cellspacing="1">
            <tr>
                <td class="columnTitle">卖方:</td>
                <td class="tableContent"><input type="text" name="seller" value=""/></td>
                <td class="columnTitle">买方:</td>
                <td class="tableContent"><input type="text" name="buyer" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">发票号:</td>
                <td class="tableContent"><input type="text" name="invoiceNo" value=""/></td>
                <td class="columnTitle">发票日期:</td>
                <td class="tableContent">
                    <input type="text" name="invoiceDate" value=""
                           onclick="WdatePicker({el:this, isShowOthers:true,dateFmt:'yyyy-MM-dd'});"/>
                </td>
            </tr>
            <tr>
                <td class="columnTitle">唛头:</td>
                <td class="tableContent"><input type="text" name="marks" value=""/></td>
                <td class="columnTitle">描述:</td>
                <td class="tableContent"><input type="text" name="descriptions" value=""/></td>
            </tr>
            <tr>
                <td class="columnTitle">报运单集合:</td>
                <td class="tableContent">
                    <!-- start -->
                    <select id='callbacks' multiple='multiple' name="exportIds">
                        <c:forEach var="export" items="${exportList}">
                            <option value='${export.id}'>${export.id}</option>
                        </c:forEach>
                        <%--<option value='elem_1'>elem 1</option>--%>
                        <%--<option value='elem_2'>elem 2</option>--%>
                        <%--<option value='elem_3'>elem 3</option>--%>
                        <%--<option value='elem_4'>elem 4</option>--%>
                        <%--...--%>
                        <%--<option value='elem_100'>elem 100</option>--%>
                    </select>
                    <!-- ends -->
                </td>
            </tr>
        </table>
    </div>
</form>
</body>
<script type="text/javascript">
    // run callbacks
    var selectedData = "";
    $('#callbacks').multiSelect({
        selectableHeader: "<input style='text-align:center' disabled type='text' class='search-input' autocomplete='off' placeholder='报运号'>",
        selectionHeader: "<input style='text-align:center' disabled type='text' class='search-input' autocomplete='off' placeholder='装箱'>",
        afterSelect: function (values) {
            var str = String(values);
            if (selectedData != "") {
                selectedData += ",";
                selectedData += str;
            } else {
                selectedData += str;
            }
            // alert(selectedData);
        },
        afterDeselect: function (values) {
            var str = String(values);
            var pos = selectedData.indexOf(values);
            var len = str.length;
            if (pos != -1) {
                if (pos == 0) {
                    selectedData = selectedData.substring(len + 1, selectedData.length);
                } else {
                    var str1 = selectedData.substring(0, pos - 1);
                    var str2 = selectedData.substring(pos + len, selectedData.length);
                    selectedData = str1 + str2;
                }
                // alert(selectedData);
            }
        }
    });
</script>
</html>

