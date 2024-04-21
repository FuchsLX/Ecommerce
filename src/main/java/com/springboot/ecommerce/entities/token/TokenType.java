package com.springboot.ecommerce.entities.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TokenType {

    UUID("UUID"),
    JWT("JWT");

    private final String name;
}
