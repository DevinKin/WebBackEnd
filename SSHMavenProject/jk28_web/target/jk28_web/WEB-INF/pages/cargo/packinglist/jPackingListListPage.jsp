<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../baselist.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
</head>

<body>
<form name="icform" method="post">

    <div id="menubar">
        <div id="middleMenubar">
            <div id="innerMenubar">
                <div id="navMenubar">
                    <ul>
                        <li id="view"><a href="#" onclick="formSubmit('packingListAction_toview','_self');this.blur();">查看</a>
                        </li>
                        <li id="new"><a href="#"
                                        onclick="formSubmit('packingListAction_tocreate','_self');this.blur();">新增</a>
                        </li>
                        <li id="update"><a href="#"
                                           onclick="formSubmit('packingListAction_toupdate','_self');this.blur();">修改</a>
                        </li>
                        <li id="delete"><a href="#"
                                           onclick="formSubmit('packingListAction_delete','_self');this.blur();">删除</a>
                        </li>
                        <li id="new"><a href="#" onclick="formSubmit('packingListAction_submit','_self');this.blur();">装箱</a>
                        </li>
                        <li id="new"><a href="#" onclick="formSubmit('packingListAction_cancel','_self');this.blur();">取消</a>
                        </li>
                        <li id="new"><a href="#" onclick="formSubmit('packingListAction_packed','_self');this.blur();">装箱</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="textbox-title">
        <img src="${ctx}/skin/default/images/icon/currency_yen.png"/>
        装箱单列表
    </div>
    </div>

    <div>


        <div class="eXtremeTable">
            <table id="ec_table" class="tableRegion" width="98%">
                <thead>
                <tr>
                    <td class="tableHeader"><input type="checkbox" name="selid" onclick="checkAll('id',this)"></td>
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
                ${links}

                <c:forEach items="${page.results}" var="o" varStatus="status">
                    <tr align="left" class="odd" onmouseover="this.className='highlight'" onmouseout="this.className='odd'">
                        <td><input type="checkbox" name="id" value="${o.id}"/></td>
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
                            <c:if test="${o.state == 1}">已上报</c:if>
                            <c:if test="${o.state == 2}">已装箱</c:if>
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

