package com.springboot.ecommerce.events.handlers;

import com.springboot.ecommerce.constants.BootstrapPermission;
import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.services.PermissionService;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.services.RoleService;
import com.springboot.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import static com.springboot.ecommerce.constants.BootstrapAccount.*;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        Permission readCustomerPerm = this.createPermissionIfNotFound(BootstrapPermission.CUSTOMER_READ.getName());
        Permission writeCustomerPerm = this.createPermissionIfNotFound(BootstrapPermission.CUSTOMER_WRITE.getName());
        Permission readStaffPerm = this.createPermissionIfNotFound(BootstrapPermission.STAFF_READ.getName());
        Permission writeStaffPerm = this.createPermissionIfNotFound(BootstrapPermission.STAFF_WRITE.getName());
        Permission readAdminPerm = this.createPermissionIfNotFound(BootstrapPermission.ADMIN_READ.getName());
        Permission writeAdminPerm = this.createPermissionIfNotFound(BootstrapPermission.ADMIN_WRITE.getName());

        Role adminRole = this.createRoleIfNotFound(BootstrapRole.ADMIN.getName(), List.of(readAdminPerm, writeAdminPerm, readStaffPerm, writeStaffPerm));
        this.createRoleIfNotFound(BootstrapRole.STAFF.getName(), List.of(readStaffPerm, writeStaffPerm));
        this.createRoleIfNotFound(BootstrapRole.CUSTOMER.getName(), List.of(readCustomerPerm, writeCustomerPerm));
        this.createSuperAccountIfNotFound(EMAIL_SUPER_ADMIN_ACCOUNT, adminRole);
        alreadySetup = true;
    }

    @Transactional
    Permission createPermissionIfNotFound(String name) {
        var permission = permissionService.getByName(name);
        if (permission == null) {
            permission = new Permission(name);
            return permissionService.save(permission);
        }
        return permission;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Permission> permissions) {
        Role role = roleService.findByName(name);
        if (role == null) {
            role = new Role(name, permissions);
            return roleService.save(role);
        }
        return role;
    }

    @Transactional
    User createSuperAccountIfNotFound(String email, Role role) {
        User superAdmin = userService.findByEmail(email);
        if (superAdmin == null) {
            superAdmin = User.builder()
                    .firstName(FIRST_NAME_SUPER_ADMIN_ACCOUNT)
                    .lastName(LAST_NAME_SUPER_ADMIN_ACCOUNT)
                    .email(email)
                    .password(passwordEncoder.encode(PASSWORD_SUPER_ADMIN_ACCOUNT))
                    .role(role)
                    .isEnabled(true)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonExpired(true)
                    .build();
            return userService.saveUser(superAdmin);
        }
        return superAdmin;
    }
}
