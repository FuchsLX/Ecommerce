package com.springboot.ecommerce.services;

import com.springboot.ecommerce.entities.user.Permission;
import org.springframework.stereotype.Service;

@Service
public interface PermissionService {
    Permission getByName(String name);
    Permission save(Permission permission);
}
