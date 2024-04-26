package com.springboot.ecommerce.entities.product;

import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import com.springboot.ecommerce.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_reviews")
@Entity
@DynamicUpdate
@EntityListeners(AuditListener.class)
public class ProductReview extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private int rating;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public User customer;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;
}