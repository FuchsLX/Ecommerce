package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.repositories.PermissionRepository;
import com.springboot.ecommerce.services.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission getByName(String name) {
        return permissionRepository.findByName(name).orElse(null);
    }

    @Override
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }
}
