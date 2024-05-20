let previousOrderStatusCntData;

const oscContainer = {
    chartContainer: 'order-chart-container',
    renderChartDiv: 'order-status-cnt-chart',
    XField: 'status',
    YField: 'order_quantity'
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
    console.log(previousOrderStatusCntData);
    if (JSON.stringify(newData) === JSON.stringify(previousOrderStatusCntData) ||
        (Array.isArray(newData) && newData.length === 0)) return
    previousOrderStatusCntData = newData;
    renderColChart(previousOrderStatusCntData, oscContainer);
}


setInterval(renderOrderCountChart, 5000)