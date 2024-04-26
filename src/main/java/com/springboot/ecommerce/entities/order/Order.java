package com.springboot.ecommerce.entities.order;

import com.springboot.ecommerce.entities.auditListener.AuditListener;
import com.springboot.ecommerce.entities.auditListener.BasicEntity;
import com.springboot.ecommerce.entities.transaction.Transaction;
import com.springboot.ecommerce.entities.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.springboot.ecommerce.entities.order.OrderStatus.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicUpdate
@EntityListeners(AuditListener.class)
@Table(name = "orders")
public class Order extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal subTotal;

    private BigDecimal itemDiscount;

    private BigDecimal total;

    private String firstName;
    private String lastName;
    private String middleName;
    private String mobile;
    private String address;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Enumerated(EnumType.STRING)
    private OrderStatus status = PROCESSING;

    @ManyToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "customer_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Transaction transaction;
}
