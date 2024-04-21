package com.springboot.ecommerce.entities.category;


import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@DynamicUpdate
public class Category extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String metaTitle;

    @Column(nullable = false)
    private String slug;

    @Column(nullable = true, columnDefinition = "TEXT")
    @Lob
    private String content;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.DETACH
    )
    @JoinColumn(
            name = "parentId",
            referencedColumnName = "id")
    private Category categoryParent;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products = new ArrayList<>();


    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
