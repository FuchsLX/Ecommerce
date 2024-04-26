package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByTags_Id(String id);

    List<Product> findAllByCategories_Id(String id);

    List<Product> findAllByCategories_IdAndTags_Id(String categoryId, String tagId);

    Product findByProductMetas_Id(String productMetaId);

    @Query("select p " +
            "from Product as p " +
            "where p.slug = :slugProduct")
    Optional<Product> findBySlugProduct(String slugProduct);


    @Query("select p " +
            "from Product as p join p.categories as c " +
            "where c.title = :categoryName")
    Page<Product> getAllProductsByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);

    @Query("select p " +
            "from Product as p join p.categories as c " +
            "where c.slug = :categorySlug")
    Page<Product> getAllProductsByCategoryId(@Param("categorySlug") String categorySlug, Pageable pageable);


    @Query("select p " +
            "from Product as p join p.tags as t " +
            "where t.slug = :tagSlug")
    Page<Product> getAllProductByTagId(@Param("tagSlug") String tagSlug, Pageable pageable);

    @Query("select distinct p " +
            "from Product as p join p.categories as c " +
            "where c.id in ?1 and p.id <> ?2")
    Page<Product> getAllRelatedProduct(List<String> relatedCategoriesId, String productId, Pageable pageable);


    @Query(value = "select distinct p.id " +
            "from products as p " +
            "inner join order_items as oi on p.id = oi.product_id " +
            "inner join orders as o on oi.order_id = o.id " +
            "inner join users as u on u.id = o.customer_id " +
            "where o.status = 'COMPLETED' and u.email = :email", nativeQuery = true)
    List<String> getAllOrderedProductIdByCustomerName(@Param("email") String customerEmail);
}
