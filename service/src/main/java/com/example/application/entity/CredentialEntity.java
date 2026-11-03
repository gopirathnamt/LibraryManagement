package com.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CREDENTIAL_TABLE")
@Getter
@Setter
public class CredentialEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRED_ID_SEQ")
    @SequenceGenerator(sequenceName = "CRED_ID_SEQ")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    private String encodedPassword;
}
