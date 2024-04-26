async function getReviewsWithPagination(pageNo) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    };
    let url= '/review/' + productId + '/' + pageNo;
    const response = await fetch(url, options);
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    const reviewsContainer = document.getElementById('review-contents');
    reviewsContainer.innerHTML = "";
    data.forEach(review => {
        const customerName = document.createElement('p');
        const rating = document.createElement('p');
        const content = document.createElement('p');
        const breakTag = document.createElement('br');
        customerName.innerText = `Name: ${review.customerName}`;
        rating.innerText = `Rating: ${review.rating}`;
        content.innerText = `Content: ${review.content}`;
        reviewsContainer.append(customerName, rating, content, breakTag, breakTag);
    });

    const reviewPagination = document.getElementById('reviews-pagination');
    reviewPagination.innerHTML = "";
    for (let i = 1; i <= totalReviewPages; i++) {
        const page = document.createElement('button');
        page.innerText = i.toString();
        page.setAttribute('type', 'button');
        if (pageNo !== i) page.setAttribute('onclick', `getReviewsWithPagination(${i})`);
        reviewPagination.appendChild(page);
    }
}