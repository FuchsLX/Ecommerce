let prevCusCntData;

const cusCntChartContainer = {
    chartContainer: 'cus-cnt-chart-container',
    renderChartDiv: 'cus-cnt-chart',
    YField: "value",
    XField: "category"
}

function convertCusCntData() {
    return [
        {
            category: "Total Customer",
            value: prevCusCntData.totalCustomer
        },
        {
            category: "Total Activated Customers",
            value: prevCusCntData.totalCustomerEnabled
        },
        {
            category: "Total Customers Updated Profile",
            value: prevCusCntData.totalCustomersUpdatedProfile
        },
        {
            category: "Total Customers Bought At Least Once",
            value: prevCusCntData.totalCustomersBoughtAtLeastOnce
        },
        {
            category: "Total Repeat Customers",
            value: prevCusCntData.totalRepeatCustomers
        },

    ]
}

async function analyticCusCnt() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/customer';
    const getCusCntApi = prefixUrl + '/cnt';

    const options = {
        method: 'GET',
        headers: {
            'Content-type' : 'application/json'
        },
    };

    const getCusCntApiResponse = await fetch(getCusCntApi, options);
    if (!getCusCntApiResponse.ok) throw new Error(`HTTP Error ! Status: ${getCusCntApiResponse.status}`);
    const newCusCntData = await getCusCntApiResponse.json();

    if (!(JSON.stringify(newCusCntData) === JSON.stringify(prevCusCntData) || (Array.isArray(newCusCntData) && newCusCntData.length === 0))) {
        prevCusCntData = newCusCntData;
        renderColChart(convertCusCntData(), cusCntChartContainer);
    }
}

setInterval(analyticCusCnt, 4000);
