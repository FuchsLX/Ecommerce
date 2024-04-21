package com.springboot.ecommerce.entities.product;


import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DynamicUpdate
@EntityListeners(AuditListener.class)
public class ProductMeta extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    private String keyProductMeta;

    @Lob
    private String content;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
