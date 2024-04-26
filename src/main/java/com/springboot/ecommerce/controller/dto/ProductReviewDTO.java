package com.springboot.ecommerce.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDTO {
    private String id;
    private String customerEmail;
    private String productId;
    private String productName;
    private String customerName;
    private String productSlug;
    private int rating;
    private String content;
}
