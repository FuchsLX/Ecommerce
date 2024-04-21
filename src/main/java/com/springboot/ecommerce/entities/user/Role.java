package com.springboot.ecommerce.entities.user;

import com.springboot.ecommerce.constants.BootstrapRole;
import com.springboot.ecommerce.entities.user.Permission;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<Permission> permissions;


    public Role(String name) {
        this.name = name;
    }

    public Role(String name, String description, Collection<Permission> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = (List<Permission>) permissions;
    }

    public Role(String name, Collection<Permission> permissions) {
        this.name = name;
        this.permissions = (List<Permission>) permissions;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permissions = (List<Permission>) permissions;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String) {
            return this.name == obj;
        } else if (obj instanceof BootstrapRole) {
            return this.name == ((BootstrapRole) obj).getName();
        }
        return false;
    }
}
