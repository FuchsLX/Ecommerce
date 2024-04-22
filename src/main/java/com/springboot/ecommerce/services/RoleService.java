package com.springboot.ecommerce.services;

import com.springboot.ecommerce.controller.dto.RoleDTO;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.entities.user.Role;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface RoleService {
    Role findByName(String name);

    RoleDTO save(RoleDTO roleDTO);

    void deleteById(String roleId);

    List<RoleDTO> getAllStaffRoles();

    RoleDTO getById(String id);

    Role createRoleIfNotFound(String name, Collection<Permission> permissions);
}
