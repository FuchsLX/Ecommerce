package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.user.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    Role findByName(String name);

    Role save(Role role);
}
