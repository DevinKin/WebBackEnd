<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <c:set var="aaa" value=""/>
    <c:set var="new" value="新增"/>
    <c:set var="view" value="查看"/>
    <c:set var="update" value="修改"/>
    <c:set var="delete" value="删除"/>
    <c:set var="role" value="角色"/>
    <!-- 遍历当前登录用户的角色列表 -->
    <c:forEach items="${_CURRENT_USER.roles }" var="role">
        <!-- 遍历每个角色下的模块 -->
        <c:forEach items="${role.modules }" var="module">
            <c:if test="${(module.ctype == 2) and module.parentName == pmodule}">
                <c:if test="${fn:contains(aaa,module.remark) eq false }">
                    <!-- 如果该模块没有输出过，则要进行输出，否则这个模块就不输出 -->
                    <c:set var="aaa" value="${aaa},${module.remark }"/>
                    <%--<li><a href="${ctx}/${module.curl}" onclick="linkHighlighted(this)" target="main"--%>
                           <%--id="aa_1">${module.cpermission }</a></li>--%>
                    <c:choose>
                        <c:when test="${module.name == '新增'}">
                            <c:set var="idValue" value="new"></c:set>
                        </c:when>
                        <c:when test="${module.name == '查看'}">
                            <c:set var="idValue" value="view"></c:set>
                        </c:when>
                        <c:when test="${module.name == '修改'}">
                            <c:set var="idValue" value="update"></c:set>
                        </c:when>
                        <c:when test="${module.name == '删除'}">
                            <c:set var="idValue" value="delete"></c:set>
                        </c:when>
                        <c:otherwise>
                            <c:set var="idValue" value="update"></c:set>
                        </c:otherwise>
                    </c:choose>
                    <li id="${idValue}"><a href="#" onclick="formSubmit('${module.curl}','_self');this.blur();">${module.name}</a></li>
                </c:if>
            </c:if>

        </c:forEach>
    </c:forEach>
</ul>