package com.springboot.ecommerce.entities.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    CASH_ON_DELIVERY("Cash on delivery"),
    CHECKOUT_VIA_PAYPAL("Checkout via Paypal");

    private final String description;
}
