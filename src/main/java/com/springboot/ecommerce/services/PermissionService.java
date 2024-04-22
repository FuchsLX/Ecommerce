package com.springboot.ecommerce.services;

import com.springboot.ecommerce.controller.dto.PermissionDTO;
import com.springboot.ecommerce.entities.user.Permission;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {

    Permission getByName(String name);

    Permission save(Permission permission);

    List<PermissionDTO> getAllPermissions();

    Permission createPermissionIfNotFound(String name);
}
