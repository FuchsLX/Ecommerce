package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.product.ProductMeta;
import com.springboot.ecommerce.repositories.ProductMetaRepository;
import com.springboot.ecommerce.services.ProductMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductMetaServiceImpl implements ProductMetaService {
    private final ProductMetaRepository productMetaRepository;

    @Override
    public void saveProductMeta(ProductMeta productMeta) {
        productMetaRepository.save(productMeta);
    }


    @Override
    public List<ProductMeta> getAllByProduct(String productId) {
        return productMetaRepository.findByProductId(productId);
    }

    @Override
    public void deleteByProduct(String productId) {
        List<ProductMeta> productMetas = this.getAllByProduct(productId);
        for (ProductMeta productMeta : productMetas){
            productMetaRepository.deleteById(productMeta.getId());
        }
    }

    @Override
    public ProductMeta getProductMetaById(String id) {
        Optional<ProductMeta> optionalProductMeta = productMetaRepository.findById(id);
        if (optionalProductMeta.isPresent()){
            return optionalProductMeta.get();
        } else {
            throw new IllegalStateException("Product Meta not found for : "+id);
        }
    }

    @Override
    public void deleteProductMeta(String productMetaId) {
        ProductMeta productMeta = this.getProductMetaById(productMetaId);
        productMeta.setProduct(null);
        this.saveProductMeta(productMeta);
        productMetaRepository.delete(productMeta);
    }
}
