function renderColChart(data, container) {
    let chartContainer = document.getElementById(container.chartContainer);
    if (document.getElementById(container.renderChartDiv)) {
        let chartDiv = document.getElementById(container.renderChartDiv);
        chartDiv.remove();
        let newChartDiv = document.createElement("div");
        newChartDiv.setAttribute("id", container.renderChartDiv);
        chartContainer.append(newChartDiv);
    }
    let root = am5.Root.new(container.renderChartDiv);

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
        categoryField: container.XField,
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
        valueYField: container.YField,
        sequencedInterpolation: true,
        categoryXField: container.XField,
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



function renderPieChart(data, container) {
    console.log('renderPieChart() is working');
    am5.ready(function() {
        let chartContainer = document.getElementById(container.chartContainer);
        if (document.getElementById(container.renderChartDiv)) {
            let chartDiv = document.getElementById(container.renderChartDiv);
            chartDiv.remove();
            let newChartDiv = document.createElement("div");
            newChartDiv.setAttribute("id", container.renderChartDiv);
            chartContainer.append(newChartDiv);
        }

        let root = am5.Root.new(container.renderChartDiv);

        root.setThemes([
            am5themes_Animated.new(root)
        ]);

        let chart = root.container.children.push(
            am5percent.PieChart.new(root, {
                endAngle: 270
            })
        );

        let series = chart.series.push(
            am5percent.PieSeries.new(root, {
                valueField: "value",
                categoryField: "category",
                endAngle: 270
            })
        );

        series.states.create("hidden", {
            endAngle: -90
        });

        series.data.setAll(data);

        series.appear(1000, 100);
    });
}

function renderStackedBarChart(data, schema) {
    am5.ready(function() {

        let chartContainer = document.getElementById(schema.chartContainer);
        if (document.getElementById(schema.renderChartDiv)) {
            let chartDiv = document.getElementById(schema.renderChartDiv);
            chartDiv.remove();
            let newChartDiv = document.createElement("div");
            newChartDiv.setAttribute("id", schema.renderChartDiv);
            chartContainer.append(newChartDiv);
        }
        let root = am5.Root.new(schema.renderChartDiv);


        let myTheme = am5.Theme.new(root);

        myTheme.rule("Grid", ["base"]).setAll({
            strokeOpacity: 0.1
        });

        root.setThemes([
            am5themes_Animated.new(root),
            myTheme
        ]);


        let chart = root.container.children.push(am5xy.XYChart.new(root, {
            panX: false,
            panY: false,
            wheelX: "panY",
            wheelY: "zoomY",
            paddingLeft: 0,
            layout: root.verticalLayout
        }));


        chart.set("scrollbarY", am5.Scrollbar.new(root, {
            orientation: "vertical"
        }));

        let yRenderer = am5xy.AxisRendererY.new(root, {});
        let yAxis = chart.yAxes.push(am5xy.CategoryAxis.new(root, {
            categoryField: schema.YField,
            renderer: yRenderer,
            tooltip: am5.Tooltip.new(root, {})
        }));

        yRenderer.grid.template.setAll({
            location: 1
        })

        yAxis.data.setAll(data);

        let xAxis = chart.xAxes.push(am5xy.ValueAxis.new(root, {
            min: 0,
            maxPrecision: 0,
            renderer: am5xy.AxisRendererX.new(root, {
                minGridDistance: 40,
                strokeOpacity: 0.1
            })
        }));


        let legend = chart.children.push(am5.Legend.new(root, {
            centerX: am5.p50,
            x: am5.p50
        }));

        function makeSeries(name, fieldName) {
            let series = chart.series.push(am5xy.ColumnSeries.new(root, {
                name: name,
                stacked: true,
                xAxis: xAxis,
                yAxis: yAxis,
                baseAxis: yAxis,
                valueXField: fieldName,
                categoryYField: schema.YField
            }));

            series.columns.template.setAll({
                tooltipText: "{name}, {categoryY}: {valueX}",
                tooltipY: am5.percent(90)
            });
            series.data.setAll(data);

            // Make stuff animate on load
            // https://www.amcharts.com/docs/v5/concepts/animations/
            series.appear();

            series.bullets.push(function () {
                return am5.Bullet.new(root, {
                    sprite: am5.Label.new(root, {
                        text: "{valueX}",
                        fill: root.interfaceColors.get("alternativeText"),
                        centerY: am5.p50,
                        centerX: am5.p50,
                        populateText: true
                    })
                });
            });

            legend.data.push(series);
        }

        for (const property of schema.properties) {
            makeSeries(property.displayName, property.fieldName)
        }
        chart.appear(1000, 100);
    });
}



function renderDragOrderingOfBar(data, container) {
    let chartContainer = document.getElementById(container.chartContainer);
    if (document.getElementById(container.renderChartDiv)) {
        let chartDiv = document.getElementById(container.renderChartDiv);
        chartDiv.remove();
        let newChartDiv = document.createElement("div");
        newChartDiv.setAttribute("id", container.renderChartDiv);
        chartContainer.append(newChartDiv);
    }

    let root = am5.Root.new(container.renderChartDiv);


    let myTheme = am5.Theme.new(root);

    myTheme.rule("Grid", ["base"]).setAll({
        strokeOpacity: 0.1
    });

    root.setThemes([
        am5themes_Animated.new(root),
        myTheme
    ]);

    let chart = root.container.children.push(
        am5xy.XYChart.new(root, {
            panX: false,
            panY: false,
            wheelX: "none",
            wheelY: "none",
            paddingLeft: 0
        })
    );

    let yRenderer = am5xy.AxisRendererY.new(root, {
        minGridDistance: 30,
        minorGridEnabled: true
    });
    yRenderer.grid.template.set("location", 1);

    let yAxis = chart.yAxes.push(
        am5xy.CategoryAxis.new(root, {
            maxDeviation: 0,
            categoryField: container.YField,
            renderer: yRenderer
        })
    );

    let xAxis = chart.xAxes.push(
        am5xy.ValueAxis.new(root, {
            maxDeviation: 0,
            min: 0,
            renderer: am5xy.AxisRendererX.new(root, {
                visible: true,
                strokeOpacity: 0.1,
                minGridDistance: 80
            })
        })
    );


    let series = chart.series.push(
        am5xy.ColumnSeries.new(root, {
            name: "Series 1",
            xAxis: xAxis,
            yAxis: yAxis,
            valueXField: container.XField,
            sequencedInterpolation: true,
            categoryYField: container.YField
        })
    );

    let columnTemplate = series.columns.template;

    columnTemplate.setAll({
        draggable: true,
        cursorOverStyle: "pointer",
        tooltipText: "drag to rearrange",
        cornerRadiusBR: 10,
        cornerRadiusTR: 10,
        strokeOpacity: 0
    });
    columnTemplate.adapters.add("fill", (fill, target) => {
        return chart.get("colors").getIndex(series.columns.indexOf(target));
    });

    columnTemplate.adapters.add("stroke", (stroke, target) => {
        return chart.get("colors").getIndex(series.columns.indexOf(target));
    });

    columnTemplate.events.on("dragstop", () => {
        sortCategoryAxis();
    });

    function getSeriesItem(category) {
        for (let i = 0; i < series.dataItems.length; i++) {
            let dataItem = series.dataItems[i];
            if (dataItem.get("categoryY") == category) {
                return dataItem;
            }
        }
    }


    function sortCategoryAxis() {
        // Sort by value
        series.dataItems.sort(function (x, y) {
            return y.get("graphics").y() - x.get("graphics").y();
        });

        let easing = am5.ease.out(am5.ease.cubic);

        // Go through each axis item
        am5.array.each(yAxis.dataItems, function (dataItem) {
            // get corresponding series item
            let seriesDataItem = getSeriesItem(dataItem.get("category"));

            if (seriesDataItem) {
                // get index of series data item
                let index = series.dataItems.indexOf(seriesDataItem);

                let column = seriesDataItem.get("graphics");

                // position after sorting
                let fy =
                    yRenderer.positionToCoordinate(yAxis.indexToPosition(index)) -
                    column.height() / 2;

                // set index to be the same as series data item index
                if (index != dataItem.get("index")) {
                    dataItem.set("index", index);

                    // current position
                    let x = column.x();
                    let y = column.y();

                    column.set("dy", -(fy - y));
                    column.set("dx", x);

                    column.animate({ key: "dy", to: 0, duration: 600, easing: easing });
                    column.animate({ key: "dx", to: 0, duration: 600, easing: easing });
                } else {
                    column.animate({ key: "y", to: fy, duration: 600, easing: easing });
                    column.animate({ key: "x", to: 0, duration: 600, easing: easing });
                }
            }
        });

        yAxis.dataItems.sort(function (x, y) {
            return x.get("index") - y.get("index");
        });
    }


    yAxis.data.setAll(data);
    series.data.setAll(data);


// Make stuff animate on load
// https://www.amcharts.com/docs/v5/concepts/animations/
    series.appear(1000);
    chart.appear(1000, 100);
}
