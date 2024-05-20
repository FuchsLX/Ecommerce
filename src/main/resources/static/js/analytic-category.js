let prevTopHighestCateRatingAvgData;
let prevTopLowestCateRatingAvgData;
let prevAllCateProdCntData;
let prevTopHighestCateProdCntData;
let prevTopHighestCateOSCData;
let prevTopHighestCateOSRData;
let prevTopHighestCateBCData;

async function analyticCategory() {
    const prefixUrl = 'http://localhost:8081/api/v1/analytics/category';
    const getTopHighestCateRatingAvgApi = prefixUrl + '/rating/10/desc';
    const getTopLowestCateRatingAvgApi = prefixUrl + '/rating/10/asc';
    const getAllCateProdCntApi = prefixUrl + '/pc';
    const getTopHighestCateProdCntApi = prefixUrl + '/pc/10/desc';
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
    const getTopLowestCateRatingAvgApiResponse = await  fetch(getTopLowestCateRatingAvgApi, options);
    const getAllCateProdCntApiResponse = await  fetch(getAllCateProdCntApi, options);
    const getTopHighestCateProdCntApiResponse = await  fetch(getTopHighestCateProdCntApi, options);
    const getTopHighestCateOSCApiResponse = await  fetch(getTopHighestCateOSCApi, options);
    const getTopHighestCateOSRApiResponse = await  fetch(getTopHighestCateOSRApi, options);
    const getTopHighestCateBCApiResponse = await  fetch(getTopHighestCateBCApi, options);

    if (!getTopHighestCateRatingAvgApiResponse.ok)  throw new Error(`HTTP error! Status: ${getTopHighestCateRatingAvgApiResponse.status}`);
    if (!getTopLowestCateRatingAvgApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopLowestCateRatingAvgApiResponse}`);
    if (!getTopHighestCateProdCntApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateProdCntApiResponse}`);
    if (!getAllCateProdCntApiResponse.ok) throw new Error(`HTTP error! Status: ${getAllCateProdCntApiResponse}`);
    if (!getTopHighestCateOSCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateOSCApiResponse}`);
    if (!getTopHighestCateOSRApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateOSRApiResponse}`);
    if (!getTopHighestCateBCApiResponse.ok) throw new Error(`HTTP error! Status: ${getTopHighestCateBCApiResponse}`);

    const newTopHighestCateRatingAvgData = await getTopHighestCateRatingAvgApiResponse.json();
    const newTopLowestCateRatingAvgData = await getTopLowestCateRatingAvgApiResponse.json();
    const newAllCateProdCntData = await getAllCateProdCntApiResponse.json();
    const newTopHighestCateProdCntData = await getTopHighestCateProdCntApiResponse.json();
    const newTopHighestCateOSCData = await getTopHighestCateOSCApiResponse.json();
    const newTopHighestCateOSRData = await getTopHighestCateOSRApiResponse.json();
    const newTopHighestCateBCData = await getTopHighestCateBCApiResponse.json();

    if (!(JSON.stringify(newTopHighestCateRatingAvgData) === JSON.stringify(prevTopHighestCateRatingAvgData) || (Array.isArray(newTopHighestCateRatingAvgData) && newTopHighestCateRatingAvgData.length === 0))) {
        prevTopHighestCateRatingAvgData = newTopHighestCateRatingAvgData;
    }
    console.log(prevTopHighestCateRatingAvgData);

    if (!(JSON.stringify(newTopLowestCateRatingAvgData) === JSON.stringify(prevTopLowestCateRatingAvgData) || (Array.isArray(newTopLowestCateRatingAvgData) && newTopLowestCateRatingAvgData.length === 0))) {
        prevTopLowestCateRatingAvgData = newTopLowestCateRatingAvgData;
    }
    console.log(prevTopLowestCateRatingAvgData);

    if (!(JSON.stringify(newAllCateProdCntData) === JSON.stringify(prevAllCateProdCntData) || (Array.isArray(newAllCateProdCntData) && newAllCateProdCntData.length === 0))) {
        prevAllCateProdCntData = newAllCateProdCntData;
    }
    console.log(prevAllCateProdCntData);

    if (!(JSON.stringify(newTopHighestCateProdCntData) === JSON.stringify(prevTopHighestCateProdCntData) || (Array.isArray(newTopHighestCateProdCntData) && newTopHighestCateProdCntData.length === 0))) {
        prevTopHighestCateProdCntData = newTopHighestCateProdCntData;
    }
    console.log(prevTopHighestCateProdCntData);

    if (!(JSON.stringify(newTopHighestCateOSCData) === JSON.stringify(prevTopHighestCateOSCData) || (Array.isArray(newTopHighestCateOSCData) && newTopHighestCateOSCData.length === 0))) {
        prevTopHighestCateOSCData = newTopHighestCateOSCData;
    }
    console.log(prevTopHighestCateOSCData);

    if (!(JSON.stringify(newTopHighestCateOSRData) === JSON.stringify(prevTopHighestCateOSRData) || (Array.isArray(newTopHighestCateOSRData) && newTopHighestCateOSRData.length === 0))) {
        prevTopHighestCateOSRData = newTopHighestCateOSRData;
    }
    console.log(prevTopHighestCateOSRData);

    if (!(JSON.stringify(newTopHighestCateBCData) === JSON.stringify(prevTopHighestCateBCData) || (Array.isArray(newTopHighestCateBCData) && newTopHighestCateBCData.length === 0))) {
        prevTopHighestCateBCData = newTopHighestCateBCData;
    }
    console.log(prevTopHighestCateBCData);
}

setInterval(analyticCategory, 5000);

