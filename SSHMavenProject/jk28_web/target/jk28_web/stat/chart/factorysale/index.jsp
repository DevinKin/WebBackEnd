<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../../WEB-INF/pages/baselist.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>amCharts examples</title>
    <link rel="stylesheet" href="../images/style.css" type="text/css">
    <script src="../amcharts/amcharts.js" type="text/javascript"></script>
    <script src="../amcharts/pie.js" type="text/javascript"></script>
    <script type="text/javascript" src="../jquery-1.11.1.min.js"></script>

    <script type="text/javascript">
        function getData() {
            var chartdata;
            $.ajax({
                url: "${ctx}/stat/statChartAction_factorysaleData",
                dataType: "json",
                async: false, // 改为同步方式,
                type: "POST",
                success: function (data) {
                    chartdata = data;
                }
            });
            return chartdata;
        }

        var chart;
        var legend;
        var chartData = getData();
        // var chartData = [
        //     {
        //         "factory": "Lithuania",
        //         "value": 260
        //     },
        //     {
        //         "factory": "Ireland",
        //         "value": 201
        //     },
        //     {
        //         "factory": "Germany",
        //         "value": 65
        //     },
        //     {
        //         "factory": "Australia",
        //         "value": 39
        //     },
        //     {
        //         "factory": "UK",
        //         "value": 19
        //     },
        //     {
        //         "factory": "Latvia",
        //         "value": 10
        //     }
        // ];
        AmCharts.ready(function () {
            // PIE CHART
            chart = new AmCharts.AmPieChart();
            chart.dataProvider = chartData;
            chart.titleField = "factory";
            chart.valueField = "value";
            chart.outlineColor = "#FFFFFF";
            chart.outlineAlpha = 0.8;
            chart.outlineThickness = 2;
            chart.balloonText = "[[title]]<br><span style='font-size:14px'><b>[[value]]</b> ([[percents]]%)</span>";
            // this makes the chart 3D
            chart.depth3D = 15;
            chart.angle = 30;

            // WRITE
            chart.write("chartdiv");
        });


    </script>
</head>

<body>
<h1>各生产厂家销量情况</h1>
<div id="chartdiv" style="width: 100%; height: 400px;"></div>
</body>

</html>