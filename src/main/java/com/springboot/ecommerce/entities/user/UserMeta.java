package com.springboot.ecommerce.entities.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserMeta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String middleName;
    private String lastName;

    private LocalDate birthday;
    private String mobile;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserMetaGender userMetaGender;

    private String address;

    @OneToOne(
            cascade = CascadeType.DETACH,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;
}
