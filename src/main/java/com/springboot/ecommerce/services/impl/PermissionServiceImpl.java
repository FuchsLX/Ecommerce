package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.controller.dto.PermissionDTO;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.repositories.PermissionRepository;
import com.springboot.ecommerce.services.PermissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    public Permission getByName(String name) {
        return permissionRepository.findByName(name).orElse(null);
    }

    @Override
    @Transactional
    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }


    @Override
    public List<PermissionDTO> getAllPermissions() {
        return permissionRepository.findAll()
                .stream()
                .map(per -> PermissionDTO.builder()
                        .id(per.getId())
                        .name(per.getName())
                        .description(per.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Permission createPermissionIfNotFound(String name) {
        Permission permission = permissionRepository.findByName(name).orElse(null);
        if (permission == null) {
            permission = new Permission(name);
            return permissionRepository.save(permission);
        }
        return permission;
    }
}
