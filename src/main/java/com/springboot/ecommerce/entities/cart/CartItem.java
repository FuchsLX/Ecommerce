package com.springboot.ecommerce.entities.cart;

import com.fasterxml.jackson.annotation.*;
import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import com.springboot.ecommerce.entities.product.Product;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditListener.class)
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cart"})
@JsonIdentityInfo(
        scope = CartItem.class,
        generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class CartItem extends BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String sku;
    private BigDecimal price;
    private BigDecimal discount;
    private Long quantity = 1L;
    private boolean active;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;


    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
