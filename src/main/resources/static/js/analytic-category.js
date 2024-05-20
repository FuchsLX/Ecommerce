let prevTopHighestCateRatingAvgData;
let prevAllCateProdCntData;
let prevTopHighestCateOSCData;
let prevTopHighestCateOSRData;
let prevTopHighestCateBCData;

const cateRatingAvgSchema = {
    IdField: "categoryId",
    YField: "categoryName",
    chartContainer: "category-rating-chart-container",
    renderChartDiv: "category-rating-chart",
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

const cateOSCSchema = {
    IdField: "categoryId",
    YField: "categoryName",
    chartContainer: "most-ordered-categories-chart-container",
    renderChartDiv: "most-ordered-categories-chart",
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

const cateOSRSchema = {
    IdField: "categoryId",
    YField: "categoryName",
    chartContainer: "highest-revenue-categories-chart-container",
    renderChartDiv: "highest-revenue-categories-chart",
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

const cateBCSchema = {
    IdField: "categoryId",
    YField: "categoryName",
    chartContainer: "highest-reorder-rate-categories-chart-container",
    renderChartDiv: "highest-reorder-rate-categories-chart",
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

async function analyticCategory() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/category';
    const getTopHighestCateRatingAvgApi = prefixUrl + '/rating/10/desc';
    const getAllCateProdCntApi = prefixUrl + '/pc';
    const getTopHighestCateOSCApi = prefixUrl + '/osc/10/desc';
    const getTopHighestCateOSRApi = prefixUrl + '/osr/10/desc';
    const getTopHighestCateBCApi = prefixUrl + '/bc/10/desc';

    const options = {
        method: 'GET',
        headers: {
            'Content-type' : 'application/json'
        },
    };

    const getTopHighestCateRatingAvgApiResponse = await  fetch(getTopHighestCateRatingAvgApi, options);
    const getAllCateProdCntApiResponse = await  fetch(getAllCateProdCntApi, options);
    const getTopHighestCateOSCApiResponse = await  fetch(getTopHighestCateOSCApi, options);
    const getTopHighestCateOSRApiResponse = await  fetch(getTopHighestCateOSRApi, options);
    const getTopHighestCateBCApiResponse = await  fetch(getTopHighestCateBCApi, options);

    if (!getTopHighestCateRatingAvgApiResponse.ok)  throw new Error(`HTTP error! Status: ${getTopHighestCateRatingAvgApiResponse.status}`);
    if (!getAllCateProdCntApiResponse.ok) throw new Error(`HTTP error! Status: ${getAllCateProdCntApiResponse}`);
    if (!getTopHighestCateOSCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateOSCApiResponse}`);
    if (!getTopHighestCateOSRApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateOSRApiResponse}`);
    if (!getTopHighestCateBCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateBCApiResponse}`);

    const newTopHighestCateRatingAvgData = await getTopHighestCateRatingAvgApiResponse.json();
    const newAllCateProdCntData = await getAllCateProdCntApiResponse.json();
    const newTopHighestCateOSCData = await getTopHighestCateOSCApiResponse.json();
    const newTopHighestCateOSRData = await getTopHighestCateOSRApiResponse.json();
    const newTopHighestCateBCData = await getTopHighestCateBCApiResponse.json();

    if (!(JSON.stringify(newTopHighestCateRatingAvgData) === JSON.stringify(prevTopHighestCateRatingAvgData) || (Array.isArray(newTopHighestCateRatingAvgData) && newTopHighestCateRatingAvgData.length === 0))) {
        prevTopHighestCateRatingAvgData = newTopHighestCateRatingAvgData;
        renderStackedBarChart(prevTopHighestCateRatingAvgData, cateRatingAvgSchema)
    }
    console.log(prevTopHighestCateRatingAvgData);

    if (!(JSON.stringify(newAllCateProdCntData) === JSON.stringify(prevAllCateProdCntData) || (Array.isArray(newAllCateProdCntData) && newAllCateProdCntData.length === 0))) {
        prevAllCateProdCntData = newAllCateProdCntData;
        renderCatePcTable(prevAllCateProdCntData);
    }
    console.log(prevAllCateProdCntData);

    if (!(JSON.stringify(newTopHighestCateOSCData) === JSON.stringify(prevTopHighestCateOSCData) || (Array.isArray(newTopHighestCateOSCData) && newTopHighestCateOSCData.length === 0))) {
        prevTopHighestCateOSCData = newTopHighestCateOSCData;
        renderStackedBarChart(prevTopHighestCateOSCData, cateOSCSchema);
    }
    console.log(prevTopHighestCateOSCData);

    if (!(JSON.stringify(newTopHighestCateOSRData) === JSON.stringify(prevTopHighestCateOSRData) || (Array.isArray(newTopHighestCateOSRData) && newTopHighestCateOSRData.length === 0))) {
        prevTopHighestCateOSRData = newTopHighestCateOSRData;
        renderStackedBarChart(prevTopHighestCateOSRData, cateOSRSchema);
    }
    console.log(prevTopHighestCateOSRData);

    if (!(JSON.stringify(newTopHighestCateBCData) === JSON.stringify(prevTopHighestCateBCData) || (Array.isArray(newTopHighestCateBCData) && newTopHighestCateBCData.length === 0))) {
        prevTopHighestCateBCData = newTopHighestCateBCData;
        renderStackedBarChart(prevTopHighestCateBCData, cateBCSchema);
    }
    console.log(prevTopHighestCateBCData);
}

function renderCatePcTable(data) {
    let productStdContainer = document.getElementById("category-pc-container");
    productStdContainer.innerHTML = "";
    let table = document.createElement("table");
    let tableHead = table.insertRow(0);
    tableHead.innerHTML = '<th>Category Id</th><th>Category Name</th><th>Product Count</th><th>Action</th>';
    for (let i = 0; i < data.length; i++) {
        let row = table.insertRow(i+1);
        row.innerHTML = `<td>${data[i].categoryId}</td>
                        <td>${data[i].categoryName}</td>
                        <td>${data[i].productCount}</td>
                        <td><button type="button"><a href="/analytics/category/${data[i].categoryId}">Detail</a></button></td>`
    }
    productStdContainer.appendChild(table);
}

setInterval(analyticCategory, 3000);

