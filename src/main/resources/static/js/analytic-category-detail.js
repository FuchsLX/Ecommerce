let prevCateRatingData;
let prevCatePCData;
let prevCateOSCData;
let prevCateOSRData;
let prevCateBCData;

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
    ) throw new Error(`No such product with id: ${productId}`);

    const newCateRatingData = await getRatingApiResponse.json()
    const newCatePCData = await getCatePCApiResponse.json()
    const newCateOSCData = await getCateOSCApiResponse.json()
    const newCateOSRData = await getCateOSRApiResponse.json()
    const newCateBCData = await getCateBCApiResponse.json();

    if (!(JSON.stringify(newCateRatingData) === JSON.stringify(prevCateRatingData) || (Array.isArray(newCateRatingData) && newCateRatingData.length === 0))) {
        prevCateRatingData = newCateRatingData;
    }
    console.log(prevCateRatingData);

    if (!(JSON.stringify(newCatePCData) === JSON.stringify(prevCatePCData) || (Array.isArray(newCatePCData) && newCatePCData.length === 0))) {
        prevCatePCData = newCatePCData;
    }
    console.log(prevCatePCData);

    if (!(JSON.stringify(newCateOSCData) === JSON.stringify(prevCateOSCData) || (Array.isArray(newCateOSCData) && newCateOSCData.length === 0))) {
        prevCateOSCData = newCateOSCData;
    }
    console.log(prevCateOSCData);

    if (!(JSON.stringify(newCateOSRData) === JSON.stringify(prevCateOSRData) || (Array.isArray(newCateOSRData) && newCateOSRData.length === 0))) {
        prevCateOSRData = newCateOSRData;
    }
    console.log(prevCateOSRData);

    if (!(JSON.stringify(newCateBCData) === JSON.stringify(prevCateBCData) || (Array.isArray(newCateBCData) && newCateBCData.length === 0))) {
        prevCateBCData = newCateBCData;
    }
    console.log(prevCateBCData);
}

setInterval(analyticCategoryDetail, 3000);


