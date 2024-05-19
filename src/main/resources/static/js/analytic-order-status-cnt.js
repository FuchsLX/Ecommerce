let previousData;

function render(data) {
    let childContainer = document.getElementById("chart-container");
    if (document.getElementById("chart")) {
        let chartDiv = document.getElementById("chartdiv");
        chartDiv.remove();
        let newChartDiv = document.createElement("div");
        newChartDiv.setAttribute("id", "chartdiv");
        childContainer.append(newChartDiv);
    }
    let root = am5.Root.new("chart");

    root.setThemes([
        am5themes_Animated.new(root)
    ]);

    let chart = root.container.children.push(am5xy.XYChart.new(root, {
        panX: true,
        panY: true,
        wheelX: "panX",
        wheelY: "zoomX",
        pinchZoomX: true,
        paddingLeft:0,
        paddingRight:1
    }));

    let cursor = chart.set("cursor", am5xy.XYCursor.new(root, {}));
    cursor.lineY.set("visible", false);


    let xRenderer = am5xy.AxisRendererX.new(root, {
        minGridDistance: 30,
        minorGridEnabled: true
    });

    xRenderer.labels.template.setAll({
        rotation: -90,
        centerY: am5.p50,
        centerX: am5.p100,
        paddingRight: 15
    });

    xRenderer.grid.template.setAll({
        location: 1
    })

    let xAxis = chart.xAxes.push(am5xy.CategoryAxis.new(root, {
        maxDeviation: 0.3,
        categoryField: "status",
        renderer: xRenderer,
        tooltip: am5.Tooltip.new(root, {})
    }));

    let yRenderer = am5xy.AxisRendererY.new(root, {
        strokeOpacity: 0.1
    })

    let yAxis = chart.yAxes.push(am5xy.ValueAxis.new(root, {
        maxDeviation: 0.3,
        renderer: yRenderer
    }));

    let series = chart.series.push(am5xy.ColumnSeries.new(root, {
        name: "Series 1",
        xAxis: xAxis,
        yAxis: yAxis,
        valueYField: "order_quantity",
        sequencedInterpolation: true,
        categoryXField: "status",
        tooltip: am5.Tooltip.new(root, {
            labelText: "{valueY}"
        })
    }));

    series.columns.template.setAll({ cornerRadiusTL: 5, cornerRadiusTR: 5, strokeOpacity: 0 });
    series.columns.template.adapters.add("fill", function (fill, target) {
        return chart.get("colors").getIndex(series.columns.indexOf(target));
    });

    series.columns.template.adapters.add("stroke", function (stroke, target) {
        return chart.get("colors").getIndex(series.columns.indexOf(target));
    });


    xAxis.data.setAll(data);
    series.data.setAll(data);

    series.appear(1000);
    chart.appear(1000, 100);
}

async function renderOrderCountChart() {
    console.log("setInterval is working!!!")
    const options = {
        method: 'GET',
        headers: {
            'Content-type': 'application/json'
        },
    };
    const url = 'http://localhost:8081/api/v1/analytics/order/counter';
    const response = await fetch(url, options);
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`)
    }
    const newData = await response.json();
    console.log(newData);
    console.log(previousData);
    if (JSON.stringify(newData) === JSON.stringify(previousData)) return
    previousData = newData;
    render(previousData);
}


setInterval(renderOrderCountChart, 5000)