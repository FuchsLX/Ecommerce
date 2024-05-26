async function getReviewsWithPagination(pageNo) {
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    };
    let url= '/review/pd/' + productId + '/' + pageNo;
    const response = await fetch(url, options);
    if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
    }
    console.log(response.status);
    console.log(response);
    const data = await response.json();
    console.log(data);
    const reviewsContainer = document.getElementById('review-contents');
    reviewsContainer.innerHTML = "";
    data.forEach(review => {
        const customerName = document.createElement('p');
        customerName.setAttribute("class", "customer-review-name");
        customerName.innerText = `Name: ${review.customerName}`;

        const rating = document.createElement('p');
        const starRating = '<svg xmlns="http://www.w3.org/2000/svg" fill="yellow" viewBox="0 0 24 24" stroke-width="1.5" stroke="gray" class="rate-icon">\n' +
            '                        <path stroke-linecap="round" stroke-linejoin="round" d="M11.48 3.499a.562.562 0 0 1 1.04 0l2.125 5.111a.563.563 0 0 0 .475.345l5.518.442c.499.04.701.663.321.988l-4.204 3.602a.563.563 0 0 0-.182.557l1.285 5.385a.562.562 0 0 1-.84.61l-4.725-2.885a.562.562 0 0 0-.586 0L6.982 20.54a.562.562 0 0 1-.84-.61l1.285-5.386a.562.562 0 0 0-.182-.557l-4.204-3.602a.562.562 0 0 1 .321-.988l5.518-.442a.563.563 0 0 0 .475-.345L11.48 3.5Z" />\n' +
            '                    </svg>';
        let stars = '';
        for (let i = 0; i < review.rating; i++) stars += starRating;
        rating.innerHTML = `<span>Rating: </span>${stars}`;

        const breakTag = document.createElement('br');

        const content = document.createElement('p');
        content.innerHTML = `Content: <span>${review.content}</span>`

        const lineDiv = document.createElement('div');
        lineDiv.setAttribute('class', 'line');

        reviewsContainer.append(customerName, rating, content, lineDiv);
    });

    const reviewPagination = document.getElementById('reviews-pagination');
    reviewPagination.innerHTML = "";
    for (let i = 1; i <= totalReviewPages; i++) {
        const paginateButton = document.createElement('button');
        paginateButton.innerText = i.toString();
        paginateButton.setAttribute('type', 'button');
        if (pageNo !== i) paginateButton.setAttribute('onclick', `getReviewsWithPagination(${i})`);
        reviewPagination.appendChild(paginateButton);
    }
}