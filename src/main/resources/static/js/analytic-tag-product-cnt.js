let previousTagProductCntData;
const  tagProdCountContainer = {
    chartContainer: 'tag-chart-container',
    renderChartDiv: 'tag-product-cnt-chart',
    XField: 'tagName',
    YField: 'productCount'
};

async function renderTagProductCntChart() {
    console.log("setInterval is working!!!")
    const options = {
        method: 'GET',
        headers: {
            'Content-type': 'application/json'
        },
    };
    const url = 'http://localhost:8081/api/v1/analytics/tag/counter';
    const response = await fetch(url, options);
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`)
    }
    const newData = await response.json();
    console.log(newData);
    console.log(previousTagProductCntData);
    if (JSON.stringify(newData) === JSON.stringify(previousTagProductCntData) ||
        (Array.isArray(newData) && newData.length === 0)) return
    previousTagProductCntData = newData;
    renderColChart(previousTagProductCntData, tagProdCountContainer);
}


setInterval(renderTagProductCntChart, 5000)