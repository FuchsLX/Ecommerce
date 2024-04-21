package com.springboot.ecommerce.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BootstrapPermission {
    CUSTOMER_WRITE("CUSTOMER:WRITE"),
    CUSTOMER_READ("CUSTOMER:READ"),
    ADMIN_WRITE("ADMIN:WRITE"),
    ADMIN_READ("ADMIN:READ"),
    STAFF_WRITE("STAFF:WRITE"),
    STAFF_READ("STAFF:READ");

    private final String name;
}
