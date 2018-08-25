<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../base.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <script type="text/javascript" src="${ctx }/components/zTree/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 获取所属模块的名称
            $("#parentId").change(function () {
                // 获取被选中option中的内容
                var parentName = $("#parentId").find("option:selected").text();
                $("#parentName").val(parentName);
            });
        });
    </script>
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
                                         onclick="formSubmit('moduleAction_update','_self');this.blur();">保存</a></li>
                        <li id="back"><a href="#" onclick="history.go(-1);">返回</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox" id="centerTextbox">
        <div class="textbox-header">
            <div class="textbox-inner-header">
                <div class="textbox-title">
                    修改模块
                </div>
            </div>
        </div>


        <div>
            <table class="commonTable" cellspacing="1">
                <tr>
                    <td class="columnTitle">模块名：</td>
                    <td class="tableContent"><input type="text" name="name" value="${name}"/></td>
                    <td class="columnTitle">层数：</td>
                    <td class="tableContent"><input type="text" name="layerNum" value="${layerNum}"/></td>
                </tr>
                <tr>
                    <td class="columnTitle">所属模块：</td>
                    <td class="tableContent">
                        <%-- struts2的标签
                            name属性代表下拉框的名称
                            list：绑定的集合名
                            headerKey：相当于首选项的option中的value属性
                            headerValue：代表首选项的文本
                            listKey：<option value="${id}">
                            listValue：代表中间的文本
                        --%>
                        <s:select name="parentId" list="#moduleList" headerKey="" headerValue="--请选择--" listKey="id"
                                  listValue="name"></s:select>
                        <input type="hidden" name="parentName" value="" id="parentName"/>
                    </td>
                </tr>
                <tr>
                    <td class="columnTitle">权限标识：</td>
                    <td class="tableContent"><input type="text" name="cpermission" value="${cpermission}"/></td>
                    <td class="columnTitle">链接：</td>
                    <td class="tableContent"><input type="text" name="curl" value="${curl}"/></td>
                </tr>
                <tr>
                    <td class="columnTitle">类型：</td>
                    <td class="tableContentAuto">
                        <input type="radio" name="ctype" value="0"
                               <c:if test="${ctype==0}">checked</c:if> class="input"/>主菜单
                        <input type="radio" name="ctype" value="1"
                               <c:if test="${ctype==1}">checked</c:if> class="input"/>左侧菜单
                        <input type="radio" name="ctype" value="2"
                               <c:if test="${ctype==2}">checked</c:if> class="input"/>按钮
                        <input type="radio" name="ctype" value="3"
                               <c:if test="${ctype==3}">checked</c:if> class="input"/>链接
                        <input type="radio" name="ctype" value="4"
                               <c:if test="${ctype==4}">checked</c:if> class="input"/>状态
                    </td>
                    <td class="columnTitle">状态：</td>
                    <td class="tableContentAuto">
                        <input type="radio" name="state" value="1"
                               <c:if test="${state==1}">checked</c:if> class="input"/>启用
                        <input type="radio" name="state" value="0"
                               <c:if test="${state==0}">checked</c:if> class="input"/>停用
                    </td>
                </tr>
                <tr>
                    <td class="columnTitle">从属：</td>
                    <td class="tableContent"><input type="text" name="belong" value="${belong}"/></td>
                    <td class="columnTitle">复用标识：</td>
                    <td class="tableContent"><input type="text" name="cwhich" value="${cwhich}"/></td>
                </tr>
                <tr>
                    <td class="columnTitle">说明：</td>
                    <td class="tableContent">
                        <textarea name="remark" style="height:120px;">${remark}</textarea>
                    </td>
                    <td class="columnTitle">排序号：</td>
                    <td class="tableContent"><input type="text" name="orderNo" value="${orderNo}"/></td>
                </tr>
            </table>
        </div>


</form>
</body>
</html>

