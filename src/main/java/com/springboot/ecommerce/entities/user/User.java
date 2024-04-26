package com.springboot.ecommerce.entities.user;

import com.springboot.ecommerce.entities.cart.Cart;
import com.springboot.ecommerce.entities.order.Order;
import com.springboot.ecommerce.entities.product.Product;
import com.springboot.ecommerce.entities.product.ProductReview;
import com.springboot.ecommerce.entities.transaction.Transaction;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, name = "email")
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAccountNonExpired;

    @Column(nullable = false)
    private boolean isAccountNonLocked;

    @Column(nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(nullable = false)
    private boolean isEnabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Product> products = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> carts = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserMeta userMeta;

    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<ProductReview> productReviews;
}
