<%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-29
  Time: 下午11:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>省市联动</title>
    <script type="text/javascript" src="<c:url value='/jquery/jquery-1.11.3.min.js'></c:url> "></script>
    <script type="text/javascript">
        $(function () {
            //页面加载成功，查询所有的省
            var url = "<c:url value='/SelectProServlet'></c:url> ";
            $.get(url,function (self) {
                var $pro =  $("#proId");
                if ($pro != null) {
                    $(self).each(function () {
                        $pro.append($("<option value='" + this.provinceid + "'>" + this.name + "</option>"))
                    });
                }
            },"json");

            //给省份下拉派发change事件
            $("#proId").change(function (self) {
                var $pid = $(this).val();
                var url = "<c:url value='/SelectCityServlet'></c:url>";

                //发送ajax请求，查询所有的市
                $.get(url, {"pid":$pid},function (self) {
                    var $city = $("#cityId");
                    //清空option
                    $city.html("<option>---请选择---</option>");
                    if (self != null) {
                        $(self).each(function () {
                            $city.append("<option value='"+ this.cityid +"'>"+ this.name +"</option>")
                        });
                    }
                },"json");
            });
        });
    </script>
</head>
<body>
<select id="proId" name="province">
    <option>---省份---</option>
</select>

<select id="cityId" name="city">
    <option>---请选择---</option>
</select>

</body>
</html>
