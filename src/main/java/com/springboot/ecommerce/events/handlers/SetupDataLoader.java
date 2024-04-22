package com.springboot.ecommerce.events.handlers;

import com.springboot.ecommerce.constants.BootstrapPermission;
import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.services.PermissionService;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.services.RoleService;
import com.springboot.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.springboot.ecommerce.constants.BootstrapAccount.*;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final UserService userService;

    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) return;
        Permission readCustomerPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.CUSTOMER_READ.getName());
        Permission writeCustomerPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.CUSTOMER_WRITE.getName());
        Permission readStaffPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.STAFF_READ.getName());
        Permission writeStaffPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.STAFF_WRITE.getName());
        Permission readAdminPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.ADMIN_READ.getName());
        Permission writeAdminPerm = permissionService.createPermissionIfNotFound(BootstrapPermission.ADMIN_WRITE.getName());

        Role adminRole = roleService.createRoleIfNotFound(BootstrapRole.ADMIN.getName(), List.of(readAdminPerm, writeAdminPerm, readStaffPerm, writeStaffPerm));
        roleService.createRoleIfNotFound(BootstrapRole.STAFF.getName(), List.of(readStaffPerm, writeStaffPerm));
        roleService.createRoleIfNotFound(BootstrapRole.CUSTOMER.getName(), List.of(readCustomerPerm, writeCustomerPerm));
        userService.createSuperAccountIfNotFound(EMAIL_SUPER_ADMIN_ACCOUNT, adminRole);
        alreadySetup = true;
    }
}
