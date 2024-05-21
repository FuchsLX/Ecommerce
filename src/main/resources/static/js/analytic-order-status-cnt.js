let prevOSCData;
let prevOSRData;

const oscContainer = {
    chartContainer: 'order-status-cnt-chart-container',
    renderChartDiv: 'order-status-cnt-chart',
    XField: 'status',
    YField: 'order_quantity'
};

const osrContainer = {
    chartContainer: 'order-revenue-chart-container',
    renderChartDiv: 'order-revenue-chart',
    XField: 'orderStatus',
    YField: 'revenue'
}

async function analyticOrder() {
    console.log("setInterval is working!!!")
    const options = {
        method: 'GET',
        headers: {
            'Content-type': 'application/json'
        },
    };
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/order';
    const getOSCDataApi = prefixUrl + '/counter';
    const getOSRDataApi = prefixUrl + '/revenue';
    const getOSCDataApiResponse = await fetch(getOSCDataApi, options);
    const getOSRDataApiResponse = await fetch(getOSRDataApi, options);

    if (!getOSCDataApiResponse.ok) throw new Error(`HTTP error! Status: ${getOSCDataApiResponse.status}`);
    if (!getOSRDataApiResponse.ok) throw new Error(`HTTP error! Status: ${getOSRDataApiResponse.status}`);

    const newOSCData = await getOSCDataApiResponse.json();
    const newOSRData = await getOSRDataApiResponse.json();


    if (!(JSON.stringify(newOSCData) === JSON.stringify(prevOSCData) || (Array.isArray(newOSCData) && newOSCData.length === 0))) {
        prevOSCData = newOSCData;
        renderColChart(prevOSCData, oscContainer);
    }

    if (!(JSON.stringify(newOSRData) === JSON.stringify(prevOSRData) || (Array.isArray(newOSRData) && newOSRData.length === 0))) {
        prevOSRData = newOSRData;
        renderColChart(prevOSRData, osrContainer);
    }

    let sumOrderCount = 0;
    for (const ele of prevOSCData) sumOrderCount += ele.order_quantity;

    let sumRevenue;
    for (const ele of prevOSRData) {
        if (ele.orderStatus === 'COMPLETED') {
            sumRevenue = ele.revenue;
        }
    }

    let sumOrderCountNode = document.getElementById("sum-order-count");
    sumOrderCountNode.innerText = `${sumOrderCount}`;
    let sumRevenueNode = document.getElementById("sum-revenue");
    sumRevenueNode.innerText = `${sumRevenue}$`;
}


setInterval(analyticOrder, 4000)