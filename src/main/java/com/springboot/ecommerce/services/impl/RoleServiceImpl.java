package com.springboot.ecommerce.services.impl;

import com.springboot.ecommerce.entities.user.Role;
import com.springboot.ecommerce.repositories.RoleRepository;
import com.springboot.ecommerce.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    @Override
    public Role findByName(String name) {
        return repository.findByName(name).orElse(null);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
