package com.example.application.entity;

import com.example.application.value.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER_INFORMATION")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_ID_SEQ")
    @SequenceGenerator(sequenceName = "USER_ID_SEQ")
    private Long id;

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE", nullable = false)
    private UserType userType = UserType.USER;

    @OneToOne(mappedBy = "user")
    private CredentialEntity credentialEntity;

}