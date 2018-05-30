<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-29
  Time: 下午3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.min.js'></c:url>"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                var url = "<c:url value='/JqueryAjaxServlet'></c:url>";
                var params = {"username": "张三"};
                // var params = "username=张三";

                /**
                 * 1.load方式
                 */
                //     $(this).load(url,params,function (self) {
                //         alert(self);
                //     })
                // })

                /**
                 * 2. get方式
                 */
                // $.get(url, params, function (self) {
                //     alert(self);
                // })


                /**
                 * 3. post方式
                 */
                // $.post(url, params, function (self) {
                //     alert(self.msg);
                // },"json")

                /**
                 * 4. ajax方式
                 */
                $.ajax({
                    url:url,
                    type:"post",
                    data:params,
                    success:function (self) {
                        alert(self.msg)
                    },
                    error:function (self) {
                        alert(self.msg)
                    },
                    dataType:"json"
                })
            })
        })
    </script>
</head>
<body>
<input type="button" value="点我" id="btn">
</body>
</html>
