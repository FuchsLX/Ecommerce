package com.springboot.ecommerce.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BootstrapRole {
    ADMIN("ADMIN"),
    STAFF("STAFF"),
    CUSTOMER("CUSTOMER");

    private final String name;
}
