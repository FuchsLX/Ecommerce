let prevProdRatingData;
let prevProdCTData;
let prevProdStdData;
let prevProdOSCData;
let prevProdOSRData;
let prevProdBCData;

const prodRatingChartContainer = {
    chartContainer: 'product-rating-chart-container',
    renderChartDiv: 'product-rating-chart'
};

const prodOSCChartContainer = {
    chartContainer: 'product-osc-chart-container',
    renderChartDiv: 'product-osc-chart'
}

async function analyticProductDetail() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/product';
    const getRatingApi = prefixUrl + `/rating/${productId}`;
    const getProductCTApi = prefixUrl + `/ct/${productId}`;
    const getProductStdApi = prefixUrl + `/std/${productId}`;
    const getProductOSCApi = prefixUrl + `/osc/${productId}`;
    const getProductOSRApi = prefixUrl + `/osr/${productId}`
    const getProductBCApi = prefixUrl + `/bc/${productId}`;

    const options = {
        method: 'GET',
        headers: {
            'Content-type' : 'application/json'
        },
    };

    const getRatingApiResponse = await fetch(getRatingApi, options);
    const getProductCTApiResponse = await fetch(getProductCTApi, options);
    const getProductStdApiResponse = await fetch(getProductStdApi, options);
    const getProductOSCApiResponse = await fetch(getProductOSCApi, options);
    const getProductOSRApiResponse = await fetch(getProductOSRApi, options);
    const getProductBCApiResponse = await fetch(getProductBCApi, options);

    if (getRatingApiResponse.status === 404 ||
        getProductCTApiResponse.status === 404 ||
        getProductStdApiResponse.status === 404 ||
        getProductOSCApiResponse.status === 404 ||
        getProductOSRApiResponse.status === 404 ||
        getProductBCApiResponse.status === 404
    ) throw new Error(`No such product with id: ${productId}`);

    const newProdRatingData = await getRatingApiResponse.json()
    const newProdCTData = await getProductCTApiResponse.json()
    const newProdStdData = await getProductStdApiResponse.json()
    const newProdOSCData = await getProductOSCApiResponse.json()
    const newProdOSRData = await getProductOSRApiResponse.json()
    const newProdBCData = await getProductBCApiResponse.json();

    if (!(JSON.stringify(newProdRatingData) === JSON.stringify(prevProdRatingData) || (Array.isArray(newProdRatingData) && newProdRatingData.length === 0))) {
        prevProdRatingData = newProdRatingData;
        renderPieChart(convertProdRatingData(), prodRatingChartContainer);
    }
    console.log(prevProdRatingData);

    if (!(JSON.stringify(newProdCTData) === JSON.stringify(prevProdCTData) || (Array.isArray(newProdCTData) && newProdCTData.length === 0))) {
        prevProdCTData = newProdCTData;
    }
    console.log(prevProdCTData);

    if (!(JSON.stringify(newProdStdData) === JSON.stringify(prevProdStdData) || (Array.isArray(newProdStdData) && newProdStdData.length === 0))) {
        prevProdStdData = newProdStdData;
    }
    console.log(prevProdStdData)

    if (!(JSON.stringify(newProdOSCData) === JSON.stringify(prevProdOSCData) || (Array.isArray(newProdOSCData) && newProdOSCData.length === 0))) {
        prevProdOSCData = newProdOSCData;
        renderPieChart(convertProdOSCData(), prodOSCChartContainer);
    }
    console.log(prevProdOSCData);

    if (!(JSON.stringify(newProdOSRData) === JSON.stringify(prevProdOSRData) || (Array.isArray(newProdOSRData) && newProdOSRData.length === 0))) {
        prevProdOSRData = newProdOSRData;
    }
    console.log(prevProdOSRData);

    if (!(JSON.stringify(newProdBCData) === JSON.stringify(prevProdBCData) || (Array.isArray(newProdBCData) && newProdBCData.length === 0))) {
        prevProdBCData = newProdBCData;
    }
    console.log(prevProdBCData);

    renderProductDetailAnalyticTable();

}


function renderProductDetailAnalyticTable() {
    let prodDetailContainer = document.getElementById("product-detail-table-container");
    prodDetailContainer.innerHTML ="";
    let table = document.createElement("table");
    table.innerHTML = `
                <tr><th>Id</th><td>${productId}</td></tr>
                <tr><th>Name</th><td>${prevProdStdData.productName}</td></tr>
                <tr><th>Price</th><td>${prevProdStdData.price}</td></tr>
                <tr><th>Discount</th><td>${prevProdStdData.discount}</td></tr>
                <tr><th>Quantity</th><td>${prevProdStdData.quantity}</td></tr>
                <tr><th>Categories</th><td>${prevProdCTData.categoryNames.join(', ')}</td></tr>
                <tr><th>Tags</th><td>${prevProdCTData.tagNames.join(', ')}</td></tr>          
                <tr><th>Rating Count</th><td>${prevProdRatingData.ratingCount}</td></tr>
                <tr><th>Rating Avg</th><td>${prevProdRatingData.ratingAvg}</td></tr>
                <tr><th>Revenue</th><td>${prevProdOSRData.completedTotalPrice}</td></tr>
                <tr><th>Unique Buyers Count</th><td>${prevProdBCData.uniqueBuyersCount}</td></tr>
                <tr><th>Reorder rate</th><td>${prevProdBCData.reorderRate}</td></tr>
                
    `;
    prodDetailContainer.appendChild(table);
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

function convertProdRatingData() {
    return [
        {
            category: "One Star",
            value: prevProdRatingData.oneStarRatingCount
        },
        {
            category: "Two Star",
            value: prevProdRatingData.twoStarRatingCount
        },
        {
            category: "Three Star",
            value: prevProdRatingData.threeStarRatingCount
        },
        {
            category: "Four Star",
            value: prevProdRatingData.fourStarRatingCount
        },
        {
            category: "Five Star",
            value: prevProdRatingData.fiveStarRatingCount
        }
    ];
}

function convertProdOSCData() {
    return [
        {
            category: "Completed",
            value: prevProdOSCData.completedCount
        },
        {
            category: "Processing",
            value: prevProdOSCData.processingCount
        },
        {
            category: "Delivered",
            value: prevProdOSCData.deliveredCount
        },
        {
            category: "Cancelled",
            value: prevProdOSCData.cancelledCount
        },
    ]
}

setInterval(analyticProductDetail, 2000);
