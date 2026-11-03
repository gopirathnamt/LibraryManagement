package com.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "BOOK_INFORMATION")
@Entity
public class BookEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOOK_ID_SEQ")
    @SequenceGenerator(sequenceName = "BOOK_ID_SEQ")
    private Long id;

    @Column(name = "BOOK_NAME", unique = true, nullable = false)
    private String bookName;

    @ManyToOne
    @JoinColumn(name = "borrowed_user_id")
    private UserEntity borrowedUser;

    private String borrowedUserName;

    private boolean isBorrowed = false;
}
