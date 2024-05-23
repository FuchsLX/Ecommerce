function searchProductManagement() {
    const inputSearch = document.getElementById('search-product');
    const searchName = inputSearch.value;
    window.location.href = `/product-management/products-list/page/1?sortField=title&sortDir=asc&searchName=${encodeURIComponent(searchName)}`;
}