package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.category.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    void saveCategory(Category category);

    List<Category> getAllCategories();

    void deleteCategory(String id);

    Category getCategoryById(String id);

    List<Category> getAllCategoriesExcept(String categoryId);

    List<String> getAllSubCategoriesOf(String categoryParentId);

    List<String> getAllCategoryParent();
}
