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
                    url: "${ctx}/stat/statChartAction_productsaleData",
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
            //         "country": "USA",
            //         "visits": 3025,
            //         "color": "#FF0F00"
            //     },
            //     {
            //         "country": "China",
            //         "visits": 1882,
            //         "color": "#FF6600"
            //     },
            //     {
            //         "country": "Japan",
            //         "visits": 1809,
            //         "color": "#FF9E01"
            //     },
            //     {
            //         "country": "Germany",
            //         "visits": 1322,
            //         "color": "#FCD202"
            //     },
            //     {
            //         "country": "UK",
            //         "visits": 1122,
            //         "color": "#F8FF01"
            //     },
            //     {
            //         "country": "France",
            //         "visits": 1114,
            //         "color": "#B0DE09"
            //     },
            //     {
            //         "country": "India",
            //         "visits": 984,
            //         "color": "#04D215"
            //     },
            //     {
            //         "country": "Spain",
            //         "visits": 711,
            //         "color": "#0D8ECF"
            //     },
            //     {
            //         "country": "Netherlands",
            //         "visits": 665,
            //         "color": "#0D52D1"
            //     },
            //     {
            //         "country": "Russia",
            //         "visits": 580,
            //         "color": "#2A0CD0"
            //     },
            //     {
            //         "country": "South Korea",
            //         "visits": 443,
            //         "color": "#8A0CCF"
            //     },
            //     {
            //         "country": "Canada",
            //         "visits": 441,
            //         "color": "#CD0D74"
            //     }
            // ];


            AmCharts.ready(function () {
                // SERIAL CHART
                chart = new AmCharts.AmSerialChart();
                chart.dataProvider = chartData;
                chart.categoryField = "product";
                chart.startDuration = 1;

                // AXES
                // category
                var categoryAxis = chart.categoryAxis;
                categoryAxis.labelRotation = 45; // this line makes category values to be rotated
                categoryAxis.gridAlpha = 0;
                categoryAxis.fillAlpha = 1;
                categoryAxis.fillColor = "#FAFAFA";
                categoryAxis.gridPosition = "start";
                categoryAxis.title = "商品名称";

                // value
                var valueAxis = new AmCharts.ValueAxis();
                valueAxis.dashLength = 5;
                valueAxis.title = "销量";
                valueAxis.axisAlpha = 0;
                chart.addValueAxis(valueAxis);

                // GRAPH
                var graph = new AmCharts.AmGraph();
                graph.valueField = "amount";
                graph.colorField = "color";
                graph.balloonText = "<b>[[category]]: [[value]]</b>";
                graph.type = "column";
                graph.lineAlpha = 0;
                graph.fillAlphas = 1;
                chart.addGraph(graph);

                // CURSOR
                var chartCursor = new AmCharts.ChartCursor();
                chartCursor.cursorAlpha = 0;
                chartCursor.zoomable = false;
                chartCursor.categoryBalloonEnabled = false;
                chart.addChartCursor(chartCursor);

                chart.creditsPosition = "top-right";

                // WRITE
                chart.write("chartdiv");
            });
        </script>
    </head>

    <body>
    <h1>产品销量的前15名</h1>
        <div id="chartdiv" style="width: 600px; height: 400px;"></div>
    </body>

</html>