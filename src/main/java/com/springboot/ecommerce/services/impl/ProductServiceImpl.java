package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.category.Category;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.entities.product.ProductMeta;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchService;
import com.springboot.ecommerce.services.ProductMetaService;
import com.springboot.ecommerce.services.ProductService;
import com.springboot.ecommerce.repositories.ProductRepository;
import com.springboot.ecommerce.search.model.product.ProductElasticSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMetaService productMetaService;
    private final ProductElasticSearchService productElasticSearchService;


    @Override
    public void saveNewProduct(Product product) {
        for (ProductMeta productMeta: product.getProductMetas()) {
            productMeta.setProduct(product);
            productMetaService.saveProductMeta(productMeta);
        }
        productRepository.save(product);
        productElasticSearchService.initProductElasticSearch(product);
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
        productElasticSearchService.updateProductElasticSearch(product);
    }

    @Override
    public Page<Product> getAllProducts(int pageNo, int pageSize, String sortField, String sortDirection) {
        return productRepository.findAll(
                this.findPaginated(pageNo, pageSize, sortField, sortDirection)
        );

    }

    @Override
    public void deleteProduct(String id) {
        productElasticSearchService.delete(id);
        productMetaService.deleteByProduct(id);
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAllByTag(String id) {
        return productRepository.findAllByTags_Id(id);
    }

    @Override
    public List<Product> findAllByCategory(String id) {
        return productRepository.findAllByCategories_Id(id);
    }

    @Override
    public Product getProductById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            return optionalProduct.get();
        } else {
            throw new IllegalStateException("Product not found for id: "+ id);
        }
    }

    @Override
    public Product getProductByProductMeta(String productMetaId) {
        return productRepository.findByProductMetas_Id(productMetaId);
    }

    @Override
    public Pageable findPaginated(int pageNo,
                                       int pageSize,
                                       String sortField,
                                       String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return  PageRequest.of(pageNo - 1, pageSize, sort);
    }

    @Override
    public Product findBySlugProduct(String slugProduct) {
        return productRepository.findBySlugProduct(slugProduct);
    }


    @Override
    public List<Product> findAllByCategoryAndTag(String categoryId, String tagId) {
        return productRepository.findAllByCategories_IdAndTags_Id(categoryId, tagId);
    }


    @Override
    public Page<Product> getAllProductByCategoryName(String categoryName, Pageable pageable) {
        return productRepository.getAllProductsByCategoryName(categoryName,pageable);
    }

    @Override
    public Page<Product> getAllProductByCategorySlug(String categorySlug, Pageable pageable) {
        return productRepository.getAllProductsByCategoryId(categorySlug, pageable);
    }


    @Override
    public Page<Product> getAllProductByTagSlug(String tagSLug, Pageable pageable) {
        return productRepository.getAllProductByTagId(tagSLug, pageable);
    }


    @Override
    public Page<Product> getAllRelatedProduct(Product product, Pageable pageable) {
        List<String> relatedCategoriesId = product.getCategories()
                .stream()
                .map(Category::getId)
                .toList();
        return productRepository.getAllRelatedProduct(relatedCategoriesId, product.getId(), pageable);
    }
}

