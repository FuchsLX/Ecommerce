package com.springboot.ecommerce.entities.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    COMPLETED,
    PROCESSING,
    DELIVERED,
    CANCELLED,
}
