package com.example.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "BOOK_WAIT_LIST")
public class WaitListEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WAIT_LIST_ID_SEQ")
    @SequenceGenerator(sequenceName = "WAIT_LIST_ID_SEQ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
