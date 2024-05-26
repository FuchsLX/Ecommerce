package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.controller.dto.ProductReviewDTO;
import com.springboot.ecommerce.entities.product.ProductReview;
import com.springboot.ecommerce.exception.InvalidReviewException;
import com.springboot.ecommerce.repositories.ProductRepository;
import com.springboot.ecommerce.repositories.ProductReviewRepository;
import com.springboot.ecommerce.repositories.UserRepository;
import com.springboot.ecommerce.services.ProductReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.springboot.ecommerce.constants.ErrorMessage.*;

@Component
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductReviewServiceImpl.class);

    @Override
    public List<ProductReviewDTO> getAllReviews(String productId, int pageNo) {
        return this.getAllReviewWithPagination(productId, pageNo, 1, "created_at", "desc").stream()
                .map(pr -> ProductReviewDTO.builder()
                        .id(pr.getId())
                        .productId(productId)
                        .customerEmail(pr.getCustomer().getEmail())
                        .productSlug(pr.getProduct().getSlug())
                        .rating(pr.getRating())
                        .content(pr.getContent())
                        .customerName(pr.getCustomer().getFirstName() + " " + pr.getCustomer().getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductReviewDTO save(ProductReviewDTO productReviewDTO) {
        if (productReviewDTO.getId() == null) return this.createNewReview(productReviewDTO);
        else return this.updateReview(productReviewDTO);
    }

    @Transactional
    protected ProductReviewDTO createNewReview(ProductReviewDTO productReviewDTO) {
        var customer = userRepository.findByEmail(productReviewDTO.getCustomerEmail()).orElse(null);
        var product = productRepository.findById(productReviewDTO.getProductId()).orElse(null);
        if (customer == null || product == null) {
            logger.error("Customer or Product not found!!!");
            throw new InvalidReviewException(INVALID_REVIEW_MESSAGE);
        }
        var newReview = ProductReview.builder()
                .rating(productReviewDTO.getRating())
                .content(productReviewDTO.getContent())
                .customer(customer)
                .product(product)
                .build();
        repository.save(newReview);
        logger.info(String.format("SAVED new product review: [Customer Email: %s] and [Product ID: %s]", productReviewDTO.getCustomerEmail(), productReviewDTO.getProductId()));
        productReviewDTO.setProductSlug(product.getSlug());
        return productReviewDTO;
    }

    @Transactional
    protected ProductReviewDTO updateReview(ProductReviewDTO productReviewDTO) {
        var reviewExisting = repository.findById(productReviewDTO.getId()).orElseThrow(() -> new InvalidReviewException(INVALID_REVIEW_MESSAGE));
        reviewExisting.setRating(productReviewDTO.getRating());
        reviewExisting.setContent(productReviewDTO.getContent());
        repository.save(reviewExisting);
        logger.info(String.format("UPDATED product review with id: %s", productReviewDTO.getId()));
        productReviewDTO.setProductSlug(reviewExisting.getProduct().getSlug());
        return productReviewDTO;
    }


    @Override
    @Transactional
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public ProductReviewDTO getReviewOfCurrentUserIfFound(String productId, String userEmail) {
        ProductReview existingReview = repository.getProductReviewByCurrentUser(productId, userEmail).orElse(null);
        if (existingReview == null) return ProductReviewDTO.builder()
                .customerEmail(userEmail)
                .productId(productId)
                .build();
        else return ProductReviewDTO.builder()
                .id(existingReview.getId())
                .customerEmail(userEmail)
                .productSlug(existingReview.getProduct().getSlug())
                .productId(productId)
                .productName(existingReview.getProduct().getTitle())
                .customerName(existingReview.getCustomer().getFirstName() + existingReview.getCustomer().getLastName())
                .rating(existingReview.getRating())
                .content(existingReview.getContent())
                .build();
    }

    @Override
    public boolean isProductOrderedByCurrentUser(String productId, String email) {
        return productRepository.getAllOrderedProductIdByCustomerName(email).contains(productId);
    }

    public Page<ProductReview> getAllReviewWithPagination(String productId) {
        return this.getAllReviewWithPagination(productId, 1, 1, "created_at", "desc");
    }


    private Page<ProductReview> getAllReviewWithPagination(String productId, int pageNo, int pageSize, String sortField, String sortDirection) {
        return repository.getAllReviews(productId, this.findPaginated(pageNo, pageSize, sortField, sortDirection));
    }

    private Pageable findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return PageRequest.of(pageNo - 1, pageSize, sort);
    }
}
