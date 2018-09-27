<html>
<head>
    <title>测试页面</title>
</head>
<body>
    学生信息:<br>
    学号:${student.id}<br>
    姓名:${student.name}<br>
    年龄:${student.age}<br>
    家庭住址:${student.address}<br>
    学生列表<br>
    <table border="1">
        <tr>
            <th>序号</th>
            <th>学号</th>
            <th>姓名</th>
            <th>年龄</th>
            <th>家庭住址</th>
        </tr>
        <#list studentList as student>
        <#if student_index%2==0>
        <tr bgcolor="#faebd7">
        <#else>
        <tr bgcolor="#7fffd4">
        </#if>
            <td>${student_index}</td>
            <td>${student.id}</td>
            <td>${student.name}</td>
            <td>${student.age}</td>
            <td>${student.address}</td>
        </tr>
        </#list>
    </table>
    <hr/>
    日期类型的处理:${date?time}<br>
    日期类型的处理:${date?date}<br>
    日期类型的处理:${date?datetime}<br>
    日期类型的处理:${date?string("yyyy/MM/dd HH:mm:sss")}<br>
    <hr/>
    null值的处理:${val!"空值"}<br>
    使用if判断null值:
    <#if val??>
        val是有值的...
    <#else>
        val值为null...
    </#if>
    <hr/>
    include标签测试:
    <#include "hello.ftl">
</body>
</html>