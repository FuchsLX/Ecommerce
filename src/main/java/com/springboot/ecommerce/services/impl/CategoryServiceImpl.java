package com.springboot.ecommerce.services.impl;


import com.springboot.ecommerce.entities.category.Category;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.services.CategoryService;
import com.springboot.ecommerce.repositories.CategoryRepository;
import com.springboot.ecommerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(String id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()){
            return optionalCategory.get();
        } else {
            throw new IllegalStateException("Category not found for id: " + id);
        }
    }

    @Override
    public void deleteCategory(String id) {
        List<Product> productList = productService.findAllByCategory(id);
        Category category = categoryRepository.findById(id).orElse(null);
        for (Product product : productList) {
            product.getCategories().remove(category);
            productService.saveProduct(product);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getAllCategoriesExcept(String categoryId) {
        List<String> subCategories = this.getAllSubCategoriesOf(categoryId);
        subCategories.add(categoryId);
        return categoryRepository.getAllCategoriesExceptId(subCategories);
    }

    @Override
    public List<String> getAllSubCategoriesOf(String categoryParentId) {
        return categoryRepository.getAllSubCategoriesOf(categoryParentId);
    }

    @Override
    public List<String> getAllCategoryParent() {
        return categoryRepository.getALlCategoryParent();
    }
}
