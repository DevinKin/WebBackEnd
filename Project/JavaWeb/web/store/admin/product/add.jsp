<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<HTML>
<HEAD>
    <meta http-equiv="Content-Language" content="zh-cn">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value='/store/css/Style1.css'></c:url>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value='/store/js/jquery-1.11.3.min.js'></c:url>" type="text/javascript"></script>
</HEAD>

<body>
<!--  -->
<form id="userAction_save_do" name="Form1" action="<c:url value='/addProduct'></c:url>" method="post"
      enctype="multipart/form-data">
    &nbsp;
    <table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee"
           style="border: 1px solid #8ba7e3" border="0">
        <tr>
            <td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
                height="26">
                <strong><STRONG>添加商品</STRONG>
                </strong>
            </td>
        </tr>

        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品名称：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="pname" value="" id="pname" class="bg"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                是否热门：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <select name="is_hot">
                    <option value="1">是</option>
                    <option value="0">否</option>
                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                市场价格：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="market_price" value="" id="market_price" class="bg"/>
            </td>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商城价格：
            </td>
            <td class="ta_01" bgColor="#ffffff">
                <input type="text" name="shop_price" value="" id="shop_price" class="bg"/>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品图片：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <input type="file" name="pimage"/>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                所属分类：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <select name="cid" id="category">
                </select>
            </td>
        </tr>
        <tr>
            <td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
                商品描述：
            </td>
            <td class="ta_01" bgColor="#ffffff" colspan="3">
                <textarea name="pdesc" rows="5" cols="30"></textarea>
            </td>
        </tr>
        <tr>
            <td class="ta_01" style="WIDTH: 100%" align="center"
                bgColor="#f5fafe" colSpan="4">
                <button type="submit" id="userAction_save_do_submit" value="确定" class="button_ok">
                    &#30830;&#23450;
                </button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <button type="reset" value="重置" class="button_cancel">&#37325;&#32622;</button>

                <FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
                <input class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
                <span id="Label1"></span>
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    $(function () {
        //发送ajax请求
        $.get("${pageContext.request.contextPath}/category?method=findAll",
            function (data) {
                //获取menu的ul标签
                var $select = $("#category");
                $select.children("#category option").remove();
                //遍历数组
                $(data).each(function () {
                    $select.append($("<option value='" + this.cid + "'>" + this.cname +"</option>"));
                });
            }, "json");
    });
</script>
</HTML>