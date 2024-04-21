package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.user.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,  String> {

    Optional<Permission> findByName(String name);

}
