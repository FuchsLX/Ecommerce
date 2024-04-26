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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.springboot.ecommerce.constants.BootstrapAccount.*;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(SetupDataLoader.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    private boolean alreadySetup = false;

    @Override
    @Transactional
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
        roleService.findByName(BootstrapRole.CUSTOMER.getName());
        userService.createSuperAccountIfNotFound(EMAIL_SUPER_ADMIN_ACCOUNT, adminRole);
//        this.createTestCustomer(1, 100);
        alreadySetup = true;
    }



    @Transactional
    protected void createTestCustomer(int start, int end) {

        for (int i = start; i <= end; i++) {
            var newCustomer = User.builder()
                    .email("cus" + i + "@test.com")
                    .password(passwordEncoder.encode("123456"))
                    .firstName("dang" + i)
                    .lastName("pech" + i)
                    .isAccountNonExpired(true)
                    .isCredentialsNonExpired(true)
                    .isAccountNonLocked(true)
                    .isEnabled(true)
                    .role(roleService.findByName(BootstrapRole.CUSTOMER.getName()))
                    .build();
            userService.saveUser(newCustomer);
            logger.info("SAVED CUSTOMER " + i);
        }
    }

}
