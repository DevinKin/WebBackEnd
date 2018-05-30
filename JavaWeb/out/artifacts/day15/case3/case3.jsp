<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-29
  Time: 下午5:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>案例3</title>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.min.js'></c:url> "></script>
    <script type="text/javascript">
        $(function () {
            //文本框keyup的时候发送ajax
            $("#tid").keyup(function (self) {
                var url = "<c:url value='/SearchKwServlet'></c:url> ";
                //获取文本框的值
                var $value = $(this).val();
                //内容为空的时候不发送ajax
                if ($value != null && $value != '') {

                }
                var kw = "kw=" + $value;
                $.post(url, kw, function (self) {
                    if (self != '') {
                        $("#did").html("");
                        var arr = self.split(",");
                        $(arr).each(function () {
                            //可以将每一个值放入一个div，将其内部插入到id为did的div中
                            $("#did").append("<div>" + this + "</div>");
                        });
                        $("#did").show();
                    }
                    else {
                        //将div隐藏
                        $("#did").hide();
                    }
                    //将div展示
                });
            });
        });
    </script>
</head>
<body>
    <center>
        <div>
            <h1>devinkin搜索</h1>
            <div>
                <input type="text" name="kw" id="tid">&nbsp;&nbsp;
                <input type="submit" value="搜索一下">
            </div>
            <div id="did" style="border: 1px solid red;width: 173px;position: relative;left: -40px;display: none;">

            </div>
        </div>
    </center>
</body>
</html>
