package com.springboot.ecommerce.services;

import com.springboot.ecommerce.controller.dto.ProductReviewDTO;
import com.springboot.ecommerce.entities.product.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductReviewService {

    List<ProductReviewDTO> getAllReviews(String productId, int pageNo);

    Page<ProductReview> getAllReviewWithPagination(String productId);

    ProductReviewDTO save(ProductReviewDTO productReviewDTO);

    void deleteById(String id);

    ProductReviewDTO getReviewOfCurrentUserIfFound(String productId, String email);

    boolean isProductOrderedByCurrentUser(String productId, String email);
}
