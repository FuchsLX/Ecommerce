package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.user.Permission;
import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.entities.user.User;
import com.springboot.ecommerce.repositories.UserRepository;
import com.springboot.ecommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.springboot.ecommerce.constants.ErrorMessage.USER_NOT_FOUND_MESSAGE;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

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
    public int enableUser(String email) {
        return repository.enableUser(email);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
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
