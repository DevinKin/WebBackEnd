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
    <script type="text/javascript" src="../js_ajax/getXmlHttpRequest.js"></script>
    <script type="text/javascript">
        function checkUsername(self) {
            //失去焦点，发送ajax请求
            //获取XMLHttpRequest核心对象
            xmlHttpRequest = getXmlHttpRequest();

            //编写回调函数
            xmlHttpRequest.onreadystatechange = function () {
                if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
                    var umsgEle = document.getElementById("username_msg");
                    var subEle = document.getElementById("sub");
                    if (xmlHttpRequest.responseText == "0") {
                        umsgEle.innerHTML = "<font color='red'>用户名已经被注册</font>";
                        subEle.disabled = "disabled";
                    }
                    else {
                        umsgEle.innerHTML = "<font color='green'>用户名可以使用</font>";
                        subEle.disabled = "";
                    }
                }
            }
            //设置请求方式和请求路径
            xmlHttpRequest.open("post", "<c:url value='/CheckUsername4AjaxServlet'></c:url>");

            //发送post请求之前设置请求头
            xmlHttpRequest.setRequestHeader("content-type","application/x-www-form-urlencoded");

            //发送请求
            xmlHttpRequest.send("username=" + self.value);
        }
    </script>
</head>
<body>
<form action="#" method="post">
    <table>
        <tr>
            <td>用户名</td>
            <td><input type="text" name="username" onblur="checkUsername(this)"></td>
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
