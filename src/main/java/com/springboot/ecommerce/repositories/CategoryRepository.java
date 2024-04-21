package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    @Transactional
    @Query("select c " +
            "from Category as c " +
            "where c.id not in ?1")
    List<Category> getAllCategoriesExceptId(List<String> categoriesId);

    @Transactional
    @Modifying
    @Query("select c.id " +
            "from Category as c " +
            "where c.categoryParent.id = ?1")
    List<String> getAllSubCategoriesOf(String categoryParentId);

    @Query("select c.categoryParent.id " +
            "from Category as c ")
    List<String> getALlCategoryParent();
}
