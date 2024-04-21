package com.springboot.ecommerce.entities.token;


import com.springboot.ecommerce.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.JWT;

    private boolean expired;
    private boolean revoked;
    private boolean confirmedRegister;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
