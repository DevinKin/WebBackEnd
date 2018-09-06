<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../../../WEB-INF/pages/baselist.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>amCharts examples</title>
        <link rel="stylesheet" href="../images/style.css" type="text/css">
        <script src="../amcharts/amcharts.js" type="text/javascript"></script>
        <script src="../amcharts/serial.js" type="text/javascript"></script>
        <script type="text/javascript" src="../jquery-1.11.1.min.js"></script>

        <script type="text/javascript">
            function getData() {
                var chartdata;
                $.ajax({
                    url: "${ctx}/stat/statChartAction_onlineinfoData",
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
            var chartData = getData();
            // var chartData = [
            //     {
            //         "hour": 0,
            //         "count": 0
            //     },
            //     {
            //         "hour": 1,
            //         "count": 22.8
            //     },
            //     {
            //         "hour": 2,
            //         "count": 23.9
            //     },
            //     {
            //         "hour": 3,
            //         "count": 25.1
            //     },
            //     {
            //         "hour": 4,
            //         "count": 27.2,
            //     },
            //     {
            //         "hour": 5,
            //         "count": 29.9,
            //     }
            //
            // ];


            AmCharts.ready(function () {
                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();

                chart.dataProvider = chartData;
                chart.categoryField = "hour";
                chart.startDuration = 1;

                chart.handDrawn = true;
                chart.handDrawnScatter = 3;

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.gridPosition = "start";
                categoryAxis.title = "时间段";

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.axisAlpha = 0;
                valueAxis.title = "人数";
                chart.addValueAxis(valueAxis);

                // line
                var graph1 = new AmCharts.AmGraph();
                graph1.type = "line";
                graph1.title = "人数";
                graph1.lineColor = "#fcd202";
                graph1.valueField = "count";
                graph1.lineThickness = 3;
                graph1.bullet = "round";
                graph1.bulletBorderThickness = 3;
                graph1.bulletBorderColor = "#fcd202";
                graph1.bulletBorderAlpha = 1;
                graph1.bulletColor = "#ffffff";
                graph1.dashLengthField = "dashLengthLine";
                graph1.balloonText = "<span style='font-size:13px;'>[[title]] in [[category]]:<b>[[value]]</b> [[additional]]</span>";
                chart.addGraph(graph1);

                // LEGEND
                var legend = new AmCharts.AmLegend();
                legend.useGraphSettings = true;
                chart.addLegend(legend);

                // WRITE
                chart.write("chartdiv");
            });
        </script>
    </head>

    <body>
        <h1>系统访问压力图</h1>
        <div id="chartdiv" style="width:600px; height:400px;"></div>
    </body>

</html>