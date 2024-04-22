package com.springboot.ecommerce.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionDTO {
    private String id;
    private String name;
    private String description;
}
