package com.springboot.ecommerce.controller.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private boolean isAccountNonLocked;
    private boolean isEnabled;
}