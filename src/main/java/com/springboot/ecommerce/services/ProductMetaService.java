package com.springboot.ecommerce.services;


import com.springboot.ecommerce.entities.product.ProductMeta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductMetaService {
    void saveProductMeta(ProductMeta productMeta);

    void deleteByProduct(String productId);

    List<ProductMeta> getAllByProduct(String productId);

    void deleteProductMeta(String productMetaId);

    ProductMeta getProductMetaById(String id);

}
