package com.springboot.ecommerce.entities.order;


import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import com.springboot.ecommerce.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
@EntityListeners(AuditListener.class)
@DynamicUpdate
public class OrderItem extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal price;
    private BigDecimal discount;
    private Long quantity;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id")
    private Product product;

}
