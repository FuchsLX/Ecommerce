let prevCateRatingData;
let prevCatePCData;
let prevCateOSCData;
let prevCateOSRData;
let prevCateBCData;

const cateRatingChartContainer = {
    chartContainer: 'category-rating-chart-container',
    renderChartDiv: 'category-rating-chart'
};

const cateOSCChartContainer = {
    chartContainer: 'category-osc-chart-container',
    renderChartDiv: 'category-osc-chart'
};

const cateOSRChartContainer = {
    chartContainer: 'category-osr-chart-container',
    renderChartDiv: 'category-osr-chart',
    XField: 'status',
    YField: 'value'
};

function convertCateRatingData() {
    return [
        {
            category: "One Star",
            value: prevCateRatingData.oneStarRatingCount
        },
        {
            category: "Two Star",
            value: prevCateRatingData.twoStarRatingCount
        },
        {
            category: "Three Star",
            value: prevCateRatingData.threeStarRatingCount
        },
        {
            category: "Four Star",
            value: prevCateRatingData.fourStarRatingCount
        },
        {
            category: "Five Star",
            value: prevCateRatingData.fiveStarRatingCount
        }
    ];
}

function convertCateOSCData() {
    return [
        {
            category: "Completed",
            value: prevCateOSCData.completedCount
        },
        {
            category: "Processing",
            value: prevCateOSCData.processingCount
        },
        {
            category: "Delivered",
            value: prevCateOSCData.deliveredCount
        },
        {
            category: "Cancelled",
            value: prevCateOSCData.cancelledCount
        },
    ]
}

function convertCateOSRData() {
    return [
        {
            status: "Completed",
            value: prevCateOSRData.completedTotalPrice
        },
        {
            status: "Processing",
            value: prevCateOSRData.processingTotalPrice
        },
        {
            status: "Delivered",
            value: prevCateOSRData.deliveredTotalPrice
        },
        {
            status: "Cancelled",
            value: prevCateOSRData.cancelledTotalPrice
        },
    ]
}

function renderCategoryDetailAnalyticTable() {
    let cateDetailContainer = document.getElementById("category-detail-table-container");
    cateDetailContainer.innerHTML ="";
    let table = document.createElement("table");
    table.setAttribute("class", "table");
    table.innerHTML = `
                <tr><th>Id</th><td>${categoryId}</td></tr>
                <tr><th>Name</th><td>${prevCatePCData.categoryName}</td></tr>
                <tr><th>Product count</th><td>${prevCatePCData.productCount}</td></tr>
                <tr><th>Rating count</th><td>${prevCateRatingData.ratingCount}</td></tr>
                <tr><th>Rating average</th><td>${prevCateRatingData.ratingAvg}</td></tr>
                <tr><th>Revenue</th><td>${prevCateOSRData.completedTotalPrice}</td></tr>
                <tr><th>Unique buyers count</th><td>${prevCateBCData.uniqueBuyersCount}</td></tr>
                <tr><th>Reorder rate</th><td>${prevCateBCData.reorderRate}</td></tr>
    `;
    cateDetailContainer.appendChild(table);
}


async function analyticCategoryDetail() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/category';
    const getRatingApi = prefixUrl + `/rating/${categoryId}`;
    const getCatePCApi = prefixUrl + `/pc/${categoryId}`;
    const getCateOSCApi = prefixUrl + `/osc/${categoryId}`;
    const getCateOSRApi = prefixUrl + `/osr/${categoryId}`
    const getCateBCApi = prefixUrl + `/bc/${categoryId}`;

    const options = {
        method: 'GET',
        headers: {
            'Content-type' : 'application/json'
        },
    };

    const getRatingApiResponse = await fetch(getRatingApi, options);
    const getCatePCApiResponse = await fetch(getCatePCApi, options);
    const getCateOSCApiResponse = await fetch(getCateOSCApi, options);
    const getCateOSRApiResponse = await fetch(getCateOSRApi, options);
    const getCateBCApiResponse = await fetch(getCateBCApi, options);

    if (getRatingApiResponse.status === 404 ||
        getCatePCApiResponse.status === 404 ||
        getCateOSCApiResponse.status === 404 ||
        getCateOSRApiResponse.status === 404 ||
        getCateBCApiResponse.status === 404
    ) throw new Error(`No such category with id: ${categoryId}`);

    const newCateRatingData = await getRatingApiResponse.json()
    const newCatePCData = await getCatePCApiResponse.json()
    const newCateOSCData = await getCateOSCApiResponse.json()
    const newCateOSRData = await getCateOSRApiResponse.json()
    const newCateBCData = await getCateBCApiResponse.json();

    if (!(JSON.stringify(newCateRatingData) === JSON.stringify(prevCateRatingData) || (Array.isArray(newCateRatingData) && newCateRatingData.length === 0))) {
        prevCateRatingData = newCateRatingData;
        renderPieChart(convertCateRatingData(), cateRatingChartContainer);
    }
    console.log(prevCateRatingData);

    if (!(JSON.stringify(newCatePCData) === JSON.stringify(prevCatePCData) || (Array.isArray(newCatePCData) && newCatePCData.length === 0))) {
        prevCatePCData = newCatePCData;
    }
    console.log(prevCatePCData);

    if (!(JSON.stringify(newCateOSCData) === JSON.stringify(prevCateOSCData) || (Array.isArray(newCateOSCData) && newCateOSCData.length === 0))) {
        prevCateOSCData = newCateOSCData;
        renderPieChart(convertCateOSCData(), cateOSCChartContainer);
    }
    console.log(prevCateOSCData);

    if (!(JSON.stringify(newCateOSRData) === JSON.stringify(prevCateOSRData) || (Array.isArray(newCateOSRData) && newCateOSRData.length === 0))) {
        prevCateOSRData = newCateOSRData;
        renderColChart(convertCateOSRData(), cateOSRChartContainer);
    }
    console.log(prevCateOSRData);

    if (!(JSON.stringify(newCateBCData) === JSON.stringify(prevCateBCData) || (Array.isArray(newCateBCData) && newCateBCData.length === 0))) {
        prevCateBCData = newCateBCData;
    }
    console.log(prevCateBCData);

    renderCategoryDetailAnalyticTable();
}

setInterval(analyticCategoryDetail, 3000);


