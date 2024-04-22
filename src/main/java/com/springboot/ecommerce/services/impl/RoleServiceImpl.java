package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.controller.dto.RoleDTO;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.RoleAlreadyExistsException;
import com.springboot.ecommerce.exception.RoleNotFoundException;
import com.springboot.ecommerce.repositories.RoleRepository;
import com.springboot.ecommerce.repositories.UserRepository;
import com.springboot.ecommerce.services.PermissionService;
import com.springboot.ecommerce.services.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.springboot.ecommerce.constants.ErrorMessage.ROLE_ALREADY_EXISTS_MESSAGE;
import static com.springboot.ecommerce.constants.ErrorMessage.ROLE_NOT_FOUND_MESSAGE;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;
    private final PermissionService permissionService;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public Role findByName(String name) {
        return repository.findByName(name).orElse(null);
    }


    @Override
    public RoleDTO getById(String id) {
        var role = repository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_MESSAGE, id)));
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(role.getPermissions()
                        .stream()
                        .map(Permission::getName)
                        .collect(Collectors.toList()))
                .build();
    }


    @Override
    @Transactional
    public RoleDTO save(RoleDTO role) {
        if (role.getId() != null) return this.updateRole(role);
        return this.createNewRole(role);
    }

    @Override
    @Transactional
    public void deleteById(String roleId) {
        var role = repository.findById(roleId).orElse(null);
        if (role == null) return;
        for (User staff: role.getUsers()) {
            staff.setRole(this.findByName(BootstrapRole.STAFF.getName()));
            userRepository.save(staff);
        }
        for (Permission per: role.getPermissions()) {
            per.getRoles().remove(role);
            permissionService.save(per);
        }
        repository.deleteById(roleId);
    }

    @Transactional
    protected RoleDTO createNewRole(RoleDTO roleDTO) {
        var role = repository.findByName(roleDTO.getName()).orElse(null);
        if (role == null) {
            List<Permission> permissions = roleDTO.getPermissions().stream()
                    .map(permissionService::getByName)
                    .collect(Collectors.toList());
            Role newRole  = Role.builder()
                    .name(roleDTO.getName())
                    .description(roleDTO.getDescription())
                    .permissions(permissions)
                    .build();
            newRole = repository.save(newRole);
            for (Permission per: permissions) {
                per.getRoles().add(newRole);
                permissionService.save(per);
            }
            roleDTO.setId(newRole.getId());
            return roleDTO;
        }
        else {
            throw new RoleNotFoundException(String.format(ROLE_ALREADY_EXISTS_MESSAGE, roleDTO.getName()));
        }
    }

    @Transactional
    protected RoleDTO updateRole(RoleDTO roleDTO) {
        var role = repository.findById(roleDTO.getId()).orElse(null);
        if (role == null) {
            logger.error("ROLE NOT FOUND !!!");
            throw new RoleNotFoundException(String.format(ROLE_NOT_FOUND_MESSAGE, roleDTO.getId()));
        }
        if (!Objects.equals(roleDTO.getName(), role.getName()) && repository.findByName(roleDTO.getName()).isPresent())
            throw new RoleAlreadyExistsException(String.format(ROLE_ALREADY_EXISTS_MESSAGE, roleDTO.getName()));
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        List<Permission> permissions = roleDTO.getPermissions().stream()
                .map(permissionService::getByName)
                .collect(Collectors.toList());
        role.setPermissions(permissions);
        repository.save(role);
        logger.info("UPDATED ROLE: " + role.getName());
        return roleDTO;
    }

    @Override
    public List<RoleDTO> getAllStaffRoles() {
        return repository.getRolesExcept(
                List.of(
                        BootstrapRole.STAFF.getName(),
                        BootstrapRole.CUSTOMER.getName(),
                        BootstrapRole.ADMIN.getName()))
                .stream()
                .map(r -> RoleDTO.builder()
                        .id(r.getId())
                        .name(r.getName())
                        .permissions(r.getPermissions()
                                    .stream()
                                    .map(Permission::getName)
                                    .collect(Collectors.toList()))
                        .description(r.getDescription())
                        .build())
                .toList();
    }

    @Override
    @Transactional
    public Role createRoleIfNotFound(String name, Collection<Permission> permissions) {
        Role role = repository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name, permissions);
            return repository.save(role);
        }
        return role;
    }
}
