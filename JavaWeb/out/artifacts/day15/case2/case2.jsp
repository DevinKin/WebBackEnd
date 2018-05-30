<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-29
  Time: 上午9:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>案例1-用户名是否被注册</title>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.min.js'></c:url>"></script>
    <script type="text/javascript">
         $(function () {
             //给username派发一个失去焦点事件，发送ajax请求
             $("input[name='username']").blur(function (ev) {
                 //获取输入的值
                 var $value = $(this).val();
                 var url = "<c:url value='/CheckUsername4AjaxServlet'></c:url>";
                 var params = "username=" + $value;
                 $.get(url,params, function (self) {
                     if (self == 1) {
                         $("#username_msg").html("<font color='green'>用户名可以使用</font>");
                     }
                     else {
                         $("#username_msg").html("<font color='red'>用户名已被注册</font>");
                     }
                 });
             });
         });
    </script>
</head>
<body>
<form action="#" method="post">
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username"></td>
            <td><span id="username_msg"></span></td>
        </tr>
        <tr>
            <td>密码</td>
            <td><input type="text" name="password"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" id="sub" ></td>
        </tr>
    </table>
</form>
</body>

</html>
