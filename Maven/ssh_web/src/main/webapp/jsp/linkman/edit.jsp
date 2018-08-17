<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <TITLE>添加联系人</TITLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
    <LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css
          rel=stylesheet>
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 发送异步的请求，获取到所有的客户
            var url = "${pageContext.request.contextPath}/customer_findAll.action";
            $.post(url, function (data) {
                $(data).each(function () {
                    var id = "${model.customer.cust_id}";
                    if (id == this.cust_id) {
                        $("#custList").append("<option value='" + this.cust_id + "' selected>" + this.cust_name + "</option>")
                        // $("#custName").val(this.cust_name);
                    } else  {
                        $("#custList").append("<option value='" + this.cust_id + "'>" + this.cust_name + "</option>")
                    }
                });
            }, "json");
        })
    </script>


    <META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<BODY>
<FORM id=form1 name=form1
      action="${pageContext.request.contextPath }/linkman_update.action"
      method=post>
    <input type="hidden" name="lkm_id" value="${model.lkm_id }"/>
    <%--<input id="custName" type="hidden" name="customer.cust_name" value="${model.customer.cust_name}"/>--%>

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
                        <TD class=manageHead>当前位置：联系人管理 &gt; 修改联系人</TD>
                    </TR>
                    <TR>
                        <TD height=2></TD>
                    </TR>
                </TABLE>
                <TABLE cellSpacing=0 cellPadding=5 border=0>
                    <tr>
                        <td>所属客户：</td>
                        <td colspan="3">
                            <%--<input type="text" name="custId" style="WIDTH: 180px" value="${model.customer.cust_name}"/>--%>
                            <select name="model.customer.cust_id" id="custList">
                            </select>
                        </td>
                    </tr>
                    <TR>
                        <td>联系人名称：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkm_name" value="${model.lkm_name}">
                        </td>
                        <td>联系人性别：</td>
                        <td>
                            <input type="radio" value="男" name="lkm_gender"
                                   <c:if test="${model.lkm_gender == '男' }">checked</c:if>>男

                            <input type="radio" value="女" name="lkmGender"
                                   <c:if test="${model.lkm_gender== '女' }">checked</c:if>>女
                        </td>
                    </TR>
                    <TR>
                        <td>联系人办公电话 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkm_phone" value="${model.lkm_phone}">
                        </td>
                        <td>联系人手机 ：</td>
                        <td>
                            <INPUT class=textbox id=sChannel2
                                   style="WIDTH: 180px" maxLength=50 name="lkm_mobile" value="${model.lkm_mobile}">
                        </td>
                    </TR>
                    <tr>
                        <td rowspan=2>
                            <INPUT class=button id=sButton2 type=submit
                                   value="保存 " name=sButton2>
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
