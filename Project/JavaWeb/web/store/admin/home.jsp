<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style>
		body
		{
			SCROLLBAR-ARROW-COLOR: #ffffff;  SCROLLBAR-BASE-COLOR: #dee3f7;
		}
    </style>
  </head>
  
<frameset rows="103,*,43" frameborder=0 border="0" framespacing="0">
  <frame src="<c:url value='/store'></c:url>/admin/top.jsp" name="topFrame" scrolling="NO" noresize>
  <frameset cols="159,*" frameborder="0" border="0" framespacing="0">
		<frame src="<c:url value='/store'></c:url>/admin/left.jsp" name="leftFrame" noresize scrolling="YES">
		<frame src="<c:url value='/store'></c:url>/admin/welcome.jsp" name="mainFrame">
  </frameset>
  <frame src="<c:url value='/store'></c:url>/admin/bottom.jsp" name="bottomFrame" scrolling="NO"  noresize>
</frameset>
</html>
