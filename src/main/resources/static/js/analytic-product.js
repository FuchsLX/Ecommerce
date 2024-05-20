let prevTopHighestProdRatingAvgData;
let prevAllProdCTData;
let prevAllProdStdData;
let prevTopHighestProdOSCData;
let prevTopHighestProdOSRData;
let prevTopHighestProdBCData;

const prodRatingAvgSchema = {
    productIdField: "productId",
    productNameField: "productName",
    chartContainer: "product-rating-chart-container",
    renderChartDiv: "product-rating-chart",
    properties: [
        {
            fieldName: "ratingAvg",
            displayName: "Rating Avg"
        },
        {
            fieldName: "ratingCount",
            displayName: "Rating Count"
        },
        {
            fieldName: "oneStarRatingCount",
            displayName: "One Star"
        },
        {
            fieldName: "twoStarRatingCount",
            displayName: "Two Star"
        },
        {
            fieldName: "threeStarRatingCount",
            displayName: "Three Star"
        },
        {
            fieldName: "oneStarRatingCount",
            displayName: "Four Star"
        },
        {
            fieldName: "oneStarRatingCount",
            displayName: "Five Star"
        },
    ]
};

const prodStdSchema = {
    productIdField: "productId",
    productNameField: "productName",
    chartContainer: "products-quantity-chart-container",
    renderChartDiv: "products-quantity-chart",
    properties: [
        {
            fieldName: "price",
            displayName: "Quantity"
        },
        {
            fieldName: "quantity",
            displayName: "Quantity"
        },
        {
            fieldName: "quantity",
            displayName: "Quantity"
        },
    ]
}

const prodOSCSchema = {
    productIdField: "productId",
    productNameField: "productName",
    chartContainer: "most-ordered-products-chart-container",
    renderChartDiv: "most-ordered-products-chart",
    properties: [
        {
            fieldName: "completedCount",
            displayName: "Completed"
        },
        {
            fieldName: "cancelledCount",
            displayName: "Cancelled"
        },
        {
            fieldName: "deliveredCount",
            displayName: "Delivered"
        },
        {
            fieldName: "processingCount",
            displayName: "Processing"
        },
    ]
};

const prodOSRSchema = {
    productIdField: "productId",
    productNameField: "productName",
    chartContainer: "highest-revenue-products-chart-container",
    renderChartDiv: "highest-revenue-products-chart",
    properties: [
        {
            fieldName: "completedTotalPrice",
            displayName: "Completed"
        },
        {
            fieldName: "cancelledTotalPrice",
            displayName: "Cancelled"
        },
        {
            fieldName: "deliveredTotalPrice",
            displayName: "Delivered"
        },
        {
            fieldName: "processingTotalPrice",
            displayName: "Processing"
        },
    ]
};

const prodBCSchema = {
    productIdField: "productId",
    productNameField: "productName",
    chartContainer: "highest-reorder-rate-products-chart-container",
    renderChartDiv: "highest-reorder-rate-products-chart",
    properties: [
        {
            fieldName: "uniqueBuyersCount",
            displayName: "Unique Buyers Count"
        },
        {
            fieldName: "repeatBuyersCount",
            displayName: "Repeat Buyers Count"
        },
        {
            fieldName: "reorderRate",
            displayName: "Reorder Rate"
        }
    ]
};

async function analyticProduct() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/product';
    const getTopHighestProdRatingAvgApi = prefixUrl + '/rating/10/desc';
    const getAllProdCTApi = prefixUrl + '/ct';
    const getAllProdStdApi = prefixUrl + '/std';
    const getTopHighestProdOSCApi = prefixUrl + '/osc/10/desc';
    const getTopHighestProdOSRApi = prefixUrl + '/osr/10/desc';
    const getTopHighestProdBCApi = prefixUrl + '/bc/10/desc';

    const options = {
        method: 'GET',
        headers: {
            'Content-type' : 'application/json'
        },
    };

    const getTopHighestProdRatingAvgApiResponse = await  fetch(getTopHighestProdRatingAvgApi, options);
    const getAllProdCTApiResponse = await  fetch(getAllProdCTApi, options);
    const getAllProdStdApiResponse = await  fetch(getAllProdStdApi, options);
    const getTopHighestProdOSCApiResponse = await  fetch(getTopHighestProdOSCApi, options);
    const getTopHighestProdOSRApiResponse = await  fetch(getTopHighestProdOSRApi, options);
    const getTopHighestProdBCApiResponse = await  fetch(getTopHighestProdBCApi, options);

    if (!getTopHighestProdRatingAvgApiResponse.ok)  throw new Error(`HTTP error! Status: ${getTopHighestProdRatingAvgApiResponse.status}`);
    if (!getAllProdCTApiResponse.ok) throw new Error(`HTTP error! Status: ${getAllProdCTApiResponse.status}`);
    if (!getAllProdStdApiResponse.ok) throw new Error(`HTTP error! Status: ${getAllProdStdApiResponse.status}`);
    if (!getTopHighestProdOSCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestProdOSCApiResponse.status}`);
    if (!getTopHighestProdOSRApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestProdOSRApiResponse.status}`);
    if (!getTopHighestProdBCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestProdBCApiResponse.status}`);

    const newTopHighestProdRatingAvgData = await getTopHighestProdRatingAvgApiResponse.json();
    const newAllProdCTData = await getAllProdCTApiResponse.json();
    const newAllProdStdData = await getAllProdStdApiResponse.json();
    const newTopHighestProdOSCData = await getTopHighestProdOSCApiResponse.json();
    const newTopHighestProdOSRData = await getTopHighestProdOSRApiResponse.json();
    const newTopHighestProdBCData = await getTopHighestProdBCApiResponse.json();

    if (!(JSON.stringify(newTopHighestProdRatingAvgData) === JSON.stringify(prevTopHighestProdRatingAvgData) || (Array.isArray(newTopHighestProdRatingAvgData) && newTopHighestProdRatingAvgData.length === 0))) {
        prevTopHighestProdRatingAvgData = newTopHighestProdRatingAvgData;
        testChart(prevTopHighestProdRatingAvgData, prodRatingAvgSchema);
    }
    console.log(prevTopHighestProdRatingAvgData);

    if (!(JSON.stringify(newAllProdCTData) === JSON.stringify(prevAllProdCTData) || (Array.isArray(newAllProdCTData) && newAllProdCTData.length === 0))) {
        prevAllProdCTData = newAllProdCTData;

    }
    console.log(prevAllProdCTData);

    if (!(JSON.stringify(newAllProdStdData) === JSON.stringify(prevAllProdStdData) || (Array.isArray(newAllProdStdData) && newAllProdStdData.length === 0))) {
        prevAllProdStdData = newAllProdStdData;
        renderTable(prevAllProdStdData);
    }
    console.log(prevAllProdStdData);

    if (!(JSON.stringify(newTopHighestProdOSCData) === JSON.stringify(prevTopHighestProdOSCData) || (Array.isArray(newTopHighestProdOSCData) && newTopHighestProdOSCData.length === 0))) {
        prevTopHighestProdOSCData = newTopHighestProdOSCData;
        testChart(prevTopHighestProdOSCData, prodOSCSchema);
    }
    console.log(prevTopHighestProdOSCData);

    if (!(JSON.stringify(newTopHighestProdOSRData) === JSON.stringify(prevTopHighestProdOSRData) || (Array.isArray(newTopHighestProdOSRData) && newTopHighestProdOSRData.length === 0))) {
        prevTopHighestProdOSRData = newTopHighestProdOSRData;
        testChart(prevTopHighestProdOSRData, prodOSRSchema);
    }
    console.log(prevTopHighestProdOSRData);

    if (!(JSON.stringify(newTopHighestProdBCData) === JSON.stringify(prevTopHighestProdBCData) || (Array.isArray(newTopHighestProdBCData) && newTopHighestProdBCData.length === 0))) {
        prevTopHighestProdBCData = newTopHighestProdBCData;
        testChart(prevTopHighestProdBCData, prodBCSchema);
    }
    console.log(prevTopHighestProdBCData);



}


function testChart(data, schema) {
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
            categoryField: schema.productNameField,
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
                categoryYField: schema.productNameField
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

function renderTable(data) {
    let productStdContainer = document.getElementById("product-std-container");
    productStdContainer.innerHTML = "";
    let table = document.createElement("table");
    let tableHead = table.insertRow(0);
    tableHead.innerHTML = '<th>Product Id</th><th>Product Name</th><th>Price</th><th>Discount</th><th>Quantity</th><th>Action</th>';
    for (let i = 0; i < data.length; i++) {
        let row = table.insertRow(i+1);
        row.innerHTML = `<td>${data[i].productId}</td>
                        <td>${data[i].productName}</td>
                        <td>${data[i].price}</td>
                        <td>${data[i].discount}</td>
                        <td>${data[i].quantity}</td>
                        <td><button type="button"><a href="/analytics/product/${data[i].productId}">Detail</a></button></td>`
    }
    productStdContainer.appendChild(table);
}

setInterval(analyticProduct, 2000);
