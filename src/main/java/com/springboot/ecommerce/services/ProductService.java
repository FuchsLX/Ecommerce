package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    void saveNewProduct(Product product);
    Page<Product> getAllProducts(int pageNo, int pageSize, String sortField, String sortDirection);

    void deleteProduct(String id);

    List<Product> findAllByTag(String id);

    List<Product> findAllByCategory(String id);

    List<Product> findAllByCategoryAndTag(String categoryId, String tagId);

    Product getProductById(String id);

    Product getProductByProductMeta(String productMetaId);

    void saveProduct(Product product);

    Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);

    Product findBySlugProduct (String slugProduct);

    Page<Product> getAllProductByCategoryName(String categoryName, Pageable pageable);

    Page<Product> getAllProductByCategorySlug(String categorySlug, Pageable pageable);

    Page<Product> getAllProductByTagSlug(String tagSlug, Pageable pageable);

    Page<Product> getAllRelatedProduct(Product product, Pageable pageable);
}
