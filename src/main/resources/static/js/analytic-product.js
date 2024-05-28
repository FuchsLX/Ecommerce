let prevTopHighestProdRatingAvgData;
let prevAllProdCTData;
let prevAllProdStdData;
let prevTopHighestProdOSCData;
let prevTopHighestProdOSRData;
let prevTopHighestProdBCData;

const prodRatingAvgSchema = {
    productIdField: "productId",
    YField: "productName",
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
            fieldName: "fourStarRatingCount",
            displayName: "Four Star"
        },
        {
            fieldName: "fiveStarRatingCount",
            displayName: "Five Star"
        },
    ]
};

const prodOSCSchema = {
    productIdField: "productId",
    YField: "productName",
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
    YField: "productName",
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
    YField: "productName",
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
        renderStackedBarChart(prevTopHighestProdRatingAvgData, prodRatingAvgSchema);
    }
    console.log(prevTopHighestProdRatingAvgData);

    if (!(JSON.stringify(newAllProdCTData) === JSON.stringify(prevAllProdCTData) || (Array.isArray(newAllProdCTData) && newAllProdCTData.length === 0))) {
        prevAllProdCTData = newAllProdCTData;

    }
    console.log(prevAllProdCTData);

    if (!(JSON.stringify(newAllProdStdData) === JSON.stringify(prevAllProdStdData) || (Array.isArray(newAllProdStdData) && newAllProdStdData.length === 0))) {
        prevAllProdStdData = newAllProdStdData;
        renderProdStdTable(prevAllProdStdData);
    }
    console.log(prevAllProdStdData);

    if (!(JSON.stringify(newTopHighestProdOSCData) === JSON.stringify(prevTopHighestProdOSCData) || (Array.isArray(newTopHighestProdOSCData) && newTopHighestProdOSCData.length === 0))) {
        prevTopHighestProdOSCData = newTopHighestProdOSCData;
        renderStackedBarChart(prevTopHighestProdOSCData, prodOSCSchema);
    }
    console.log(prevTopHighestProdOSCData);

    if (!(JSON.stringify(newTopHighestProdOSRData) === JSON.stringify(prevTopHighestProdOSRData) || (Array.isArray(newTopHighestProdOSRData) && newTopHighestProdOSRData.length === 0))) {
        prevTopHighestProdOSRData = newTopHighestProdOSRData;
        renderStackedBarChart(prevTopHighestProdOSRData, prodOSRSchema);
    }
    console.log(prevTopHighestProdOSRData);

    if (!(JSON.stringify(newTopHighestProdBCData) === JSON.stringify(prevTopHighestProdBCData) || (Array.isArray(newTopHighestProdBCData) && newTopHighestProdBCData.length === 0))) {
        prevTopHighestProdBCData = newTopHighestProdBCData;
        renderStackedBarChart(prevTopHighestProdBCData, prodBCSchema);
    }
    console.log(prevTopHighestProdBCData);



}



function renderProdStdTable(data) {
    let productStdContainer = document.getElementById("product-std-container");
    productStdContainer.innerHTML = "";
    let table = document.createElement("table");
    table.setAttribute("class", "table");
    let tableHead = table.insertRow(0);
    tableHead.innerHTML = '<th>Product Id</th><th>Product Name</th><th>Price</th><th>Discount (%)</th><th>Quantity</th><th>Action</th>';
    for (let i = 0; i < data.length; i++) {
        let row = table.insertRow(i+1);
        row.innerHTML = `<td>${data[i].productId}</td>
                        <td>${data[i].productName}</td>
                        <td>${data[i].price}</td>
                        <td>${data[i].discount}</td>
                        <td>${data[i].quantity}</td>
                        <td>
                            <a href="/analytics/product/${data[i].productId}">
                                <button type="button" class="btn btn-primary btn-rounded btn-fw">
                                    Detail
                                </button>
                            </a>`;
    }
    productStdContainer.appendChild(table);
}

setInterval(analyticProduct, 2000);
