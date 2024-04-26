package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.product.ProductReview;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, String> {

    @Transactional
    @Modifying
    @Query("delete from ProductReview as pr where pr.product.id = :productId")
    void deleteByProductId(@Param("productId") String productId);

    @Query(value = "select pr.* " +
            "from product_reviews as pr " +
            "inner join users as u on pr.customer_id = u.id " +
            "where pr.product_id = :productId and u.email  = :email", nativeQuery = true)
    Optional<ProductReview> getProductReviewByCurrentUser(@Param("productId") String productId, @Param("email") String currentEmail);

    @Query(value = "select pr.* " +
            "from product_reviews as pr " +
            "where pr.product_id = :productId ", nativeQuery = true)
    Page<ProductReview> getAllReviews(@Param("productId") String productId, Pageable pageable);
}
