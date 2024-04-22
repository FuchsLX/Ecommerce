package com.springboot.ecommerce.repositories;

import com.springboot.ecommerce.entities.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);

    @Query("""
        select r 
        from Role as r 
        where r.name not in :roles
        """)
    List<Role> getRolesExcept(@Param("roles") List<String> roles);
}
