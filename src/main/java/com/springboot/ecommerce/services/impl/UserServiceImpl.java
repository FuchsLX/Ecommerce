package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.controller.dto.StaffDTO;
import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.exception.EmailAlreadyTakenException;
import com.springboot.ecommerce.exception.RoleNotFoundException;
import com.springboot.ecommerce.exception.StaffAccountNotFoundException;
import com.springboot.ecommerce.repositories.RoleRepository;
import com.springboot.ecommerce.repositories.UserRepository;
import com.springboot.ecommerce.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.springboot.ecommerce.constants.BootstrapAccount.*;
import static com.springboot.ecommerce.constants.ErrorMessage.*;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserDetailsByUsername(String email) {
        User user = repository.loadByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(USER_NOT_FOUND_MESSAGE, email)
                ));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), this.getAuthorities(user.getRole()));
    }

    @Override
    public void enableUser(String email) {
        repository.enableUser(email);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }


    @Override
    public List<StaffDTO> getAllStaffAccount() {
        return repository.getAllUserWithRoleNotIn(List.of(BootstrapRole.ADMIN.getName(), BootstrapRole.CUSTOMER.getName())).stream()
                .map(u -> StaffDTO.builder()
                        .id(u.getId())
                        .firstName(u.getFirstName())
                        .lastName(u.getLastName())
                        .email(u.getEmail())
                        .isAccountNonLocked(u.isAccountNonLocked())
                        .isEnabled(u.isEnabled())
                        .role(u.getRole().getName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void saveStaffAccount(StaffDTO staffDTO) {
        if (staffDTO.getId() != null) this.updateStaffAccount(staffDTO);
        else this.createStaffAccount(staffDTO);
    }

    @Override
    public void deleteStaffAccountById(String userId) {
        repository.deleteById(userId);
    }

    @Override
    public StaffDTO getStaffAccById(String id) {
        User staff = repository.findById(id)
                .orElseThrow(() -> new StaffAccountNotFoundException(String.format(STAFF_ACCOUNT_NOT_FOUND_MESSAGE, id)));
        return StaffDTO.builder()
                .id(id)
                .firstName(staff.getFirstName())
                .lastName(staff.getLastName())
                .email(staff.getEmail())
                .password("")
                .isEnabled(staff.isEnabled())
                .isAccountNonLocked(staff.isAccountNonLocked())
                .role(staff.getRole().getName())
                .build();
    }

    @Override
    @Transactional
    public void createSuperAccountIfNotFound(String email, Role role) {
        User superAdmin = repository.findByEmail(email).orElse(null);
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
                    .isCredentialsNonExpired(true)
                    .build();
            repository.save(superAdmin);
        }
    }


    @Transactional
    protected void createStaffAccount(StaffDTO newStaff) {
        Optional<User> existingStaff = repository.findByEmail(newStaff.getEmail());
        if (existingStaff.isPresent()) throw new EmailAlreadyTakenException(EMAIL_ALREADY_TAKEN_MESSAGE);

        var staff = User.builder()
                .firstName(newStaff.getFirstName())
                .lastName(newStaff.getLastName())
                .email(newStaff.getEmail())
                .password(passwordEncoder.encode(newStaff.getPassword()))
                .isEnabled(newStaff.isEnabled())
                .isAccountNonLocked(newStaff.isAccountNonLocked())
                .isCredentialsNonExpired(true)
                .isAccountNonExpired(true)
                .role(roleRepository.findByName(newStaff.getRole()).orElse(null))
                .build();
        repository.save(staff);
    }


    @Transactional
    protected void updateStaffAccount(StaffDTO staffDTO) {
        var staff = repository.findById(staffDTO.getId())
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, staffDTO.getEmail())));
        Role role;
        if (staffDTO.getRole().isEmpty()) role = null;
        else  role = roleRepository.findByName(staffDTO.getRole()).orElseThrow(
                () -> new RoleNotFoundException(String.format(ROLE_NOT_FOUND_MESSAGE, staffDTO.getRole()))
        );
        staff.setFirstName(staffDTO.getFirstName());
        staff.setLastName(staffDTO.getLastName());
        staff.setRole(role);
        staff.setEnabled(staffDTO.isEnabled());
        staff.setAccountNonLocked(staffDTO.isAccountNonLocked());
    }


    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return this.getGrantedAuthorities(this.getPermission(role));
    }

    private List<String> getPermission(Role role) {
        List<String> permissions = new ArrayList<>();
        List<Permission> collection = new ArrayList<>(role.getPermissions());
        permissions.add("ROLE_" + role.getName());
        for (Permission p: collection) permissions.add(p.getName());
        return permissions;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> permissions) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission: permissions) authorities.add(new SimpleGrantedAuthority(permission));
        return authorities;
    }
}
