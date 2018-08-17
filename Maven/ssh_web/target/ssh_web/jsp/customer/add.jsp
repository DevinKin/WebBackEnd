<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <TITLE>添加客户</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
    <LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
          rel=stylesheet>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        // 页面的加载
        $(function () {
            var url = "${pageContext.request.contextPath}/dict_findByCode.action";
            var params = {"dict_type_code": "006"};
            $.post(url, params, function (data) {
                $(data).each(function (i, n) {
                    $("#levelId").append("<option value='" + n.dict_id + "'>" + n.dict_item_name + "</option>")
                });
            }, "json");

            // 获取来源
            var params = {"dict_type_code": "002"};
            $.post(url, params, function (data) {
                $(data).each(function (i, n) {
                    //Jquery的DOM操作
                    $("#sourceId").append("<option value='" + n.dict_id + "'>" + n.dict_item_name + "</option>")
                });
            }, "json");

            // 发送异步的请求，获取到所有的联系人
            var url = "${pageContext.request.contextPath}/linkman_findAll.action";
            $.post(url, function (data) {
                $(data).each(function () {
                    $("#linkmanId").append("<option value='" + this.lkm_id + "'>" + this.lkm_name + "</option>")
                });
            }, "json");
        });

    </script>

    <META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
<FORM id=form1 name=form1
      action="${pageContext.request.contextPath }/customer_save.action" enctype="multipart/form-data"
      method=post>


    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg"
                              border=0></TD>
            <TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg"
                height=20></TD>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg"
                              border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15 background=${pageContext.request.contextPath }/images/new_022.jpg><IMG
                    src="${pageContext.request.contextPath }/images/new_022.jpg" border=0></TD>
            <TD vAlign=top width="100%" bgColor=#ffffff>
                <TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
                    <TR>
                        <TD class=manageHead>当前位置：客户管理 &gt; 添加客户</TD>
                    </TR>
                    <TR>
                        <TD height=2></TD>
                    </TR>
                </TABLE>

                <TABLE cellSpacing=0 cellPadding=5 border=0>


                    <TR>
                        <td>客户名称：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="cust_name">
                        </td>
                        <td>客户级别 ：</td>
                        <TD>
                            <select name="level.dict_id" id="levelId">
                            </select>
                        </TD>
                    </TR>

                    <TR>

                        <td>信息来源 ：</td>
                        <TD>
                            <select name="source.dict_id" id="sourceId">
                            </select>
                        </TD>

                        <td>联系人：</td>
                        <td>
                            <select name="lkm_id" id="linkmanId">
                                <option value="">无</option>
                            </select>
                        </td>
                    </TR>

                    <TR>


                        <td>固定电话 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="cust_phone">
                        </td>
                        <td>移动电话 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="cust_mobile">
                        </td>
                    </TR>

                    <TR>
                        <td>联系地址 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="custAddress">
                        </td>
                        <td>邮政编码 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="custZip">
                        </td>
                    </TR>
                    <TR>
                        <td>客户传真 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="custFax">
                        </td>
                        <td>上传资质 ：</td>
                        <td>
                            <INPUT type="file" class=type id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="upload"/>
                        </td>
                    </TR>
                    <tr>
                        <td rowspan=2>
                            <INPUT class=button id=sButton2 type=submit
                                   value=" 保存 " name=sButton2>
                        </td>
                    </tr>
                </TABLE>


            </TD>
            <TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
                <IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
    <TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
        <TBODY>
        <TR>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg"
                              border=0></TD>
            <TD align=middle width="100%"
                background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
            <TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"
                              border=0></TD>
        </TR>
        </TBODY>
    </TABLE>
</FORM>
</BODY>
</HTML>
