<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>菜单</title>
    <link href="<c:url value='/store/css/left.css'></c:url>" rel="stylesheet" type="text/css"/>
    <link rel="StyleSheet" href="<c:url value='/store/css/dtree.css'></c:url>" type="text/css"/>
    <script src="<c:url value='/store'></c:url>/js/jquery-1.11.3.min.js" type="text/javascript"></script>
</head>
<body>
<table width="100" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td height="12"></td>
    </tr>
</table>
<table width="100%" border="0">
    <tr>
        <td>
            <div class="dtree">

                <a href="javascript: d.openAll();">展开所有</a> | <a href="javascript: d.closeAll();">关闭所有</a>

                <script type="text/javascript" src="<c:url value='/store/js/dtree.js'></c:url>"></script>
                <script type="text/javascript">
                    cArray = new Array();
                    cids = new Array();
                    $(function () {
                        d = new dTree('d');
                        d.add('01', -1, '系统菜单树');
                        d.add('0101', '01', '用户管理', '', '', 'mainFrame');
                        d.add('010101', '0101', '用户管理', '<c:url value='/store'></c:url>/userAdmin_findAll.action?page=1', '', 'mainFrame');
                        d.add('0102', '01', '分类管理', '', '', 'mainFrame');
                        d.add('010201', '0102', '展示所有', '<c:url value='/adminCategory?method=findAll'></c:url>', '', 'mainFrame');
                        d.add('010202', '0102', '添加分类', '<c:url value='/adminCategory?method=addCategoryUI'></c:url>', '', 'mainFrame');
                        d.add('0104', '01', '商品管理');
                        d.add('010401', '0104', '所有商品', '<c:url value='/adminProduct?method=findByPage&currentPage=1&pflag=1'></c:url>', '', 'mainFrame');
                        d.add('010402', '0104', '已下架商品', '<c:url value='/adminProduct?method=findByPage&currentPage=1&pflag=0'></c:url>', '', 'mainFrame');
                        d.add('010403', '0104', '商品分类', '<c:url value='/adminProduct?method=findByPage&currentPage=1'></c:url>', '', 'mainFrame');
                        d.add('010404', '0104', '添加商品', '<c:url value='/adminProduct?method=addProductUI'></c:url>', '', 'mainFrame');
                        d.add('0105', '01', '订单管理');
                        d.add('010501', '0105', '所有订单', '<c:url value='/adminOrder?method=findAllByState&state='></c:url>', '', 'mainFrame');
                        d.add('010502', '0105', '未付款订单', '<c:url value='/adminOrder?method=findAllByState&state=0'></c:url>', '', 'mainFrame');
                        d.add('010503', '0105', '已付款订单', '<c:url value='/adminOrder?method=findAllByState&state=1'></c:url>', '', 'mainFrame');
                        d.add('010504', '0105', '已发货订单', '<c:url value='/adminOrder?method=findAllByState&state=2'></c:url>', '', 'mainFrame');
                        d.add('010505', '0105', '已收货订单', '<c:url value='/adminOrder?method=findAllByState&state=3'></c:url>', '', 'mainFrame');
                        d.add('010506', '0105', '已完成订单', '<c:url value='/adminOrder?method=findAllByState&state=4'></c:url>', '', 'mainFrame');
                        //发送ajax请求
                        $.get("${pageContext.request.contextPath}/category?method=findAll",
                            function (data) {
                                $(data).each(function () {
                                    cArray.push(this.cname);
                                    // alert(cArray.pop());
                                    cids.push(this.cid);
                                });
                                for (var i = 0; i < cArray.length; i++) {
                                    var str = '010403' + (i + 1);
                                    d.add(str, '010403', cArray[i], '${pageContext.request.contextPath}/adminProduct?method=findByPage&currentPage=1&pflag=1&cid=' + cids[i], '', 'mainFrame');
                                }
                                document.write(d);
                            }, "json");
                    });
                </script>
            </div>
        </td>
    </tr>
</table>


</body>
</html>
