<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: king
  Date: 18-5-23
  Time: 下午12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>获取复杂的数据</title>
</head>
<body>
<%
    //往request域中放入数组
    request.setAttribute("arr", new String[] {"123", "aaa", "bbb"});

    //往request域中放入list
    List list = new ArrayList();
    list.add("aaa");
    list.add("bbb");
    list.add("ccc");
    request.setAttribute("list", list);

    Map map = new HashMap();
    map.put("username", "tom");
    map.put("age", "18");
    request.setAttribute("map", map);

    request.setAttribute("arr.age", 18);
%>
获取域中的数组：<br>
老方式：<%=((String[])request.getAttribute("arr"))[1]%><br>
EL方式：${arr[1]}<br>
获取域中的list：<br>
老方式：<%=((List)request.getAttribute("list")).get(1)%><br>
EL方式：${list[1]}<br>
list的长度：${list.size()}<br>
获取域中的map：<br>
老方式：<%=((Map)request.getAttribute("map")).get("username")%><br>
EL方式：${map.age}<br>


获取特殊名字的数据：<br>
${requestScope["arr.age"]}

</body>
</html>
