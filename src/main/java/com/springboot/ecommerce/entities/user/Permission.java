package com.springboot.ecommerce.entities.user;

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
@Table(name = "permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public Permission(String name) {
        this.name = name;
    }

    public Permission(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Permission(String name, Collection<Role> roles) {
        this.name = name;
        this.roles = (List<Role>) roles;
    }

    public Permission(String name, String description, Collection<Role> roles) {
        this.name = name;
        this.description = description;
        this.roles = (List<Role>) roles;
    }
}
