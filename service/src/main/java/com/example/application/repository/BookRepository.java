package com.example.application.repository;

import com.example.application.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByBookNameAndDeletedFalse(String bookName);

    Optional<BookEntity> findByBookNameAndBorrowedUserUsernameAndDeletedFalse(String bookName, String username);

    List<BookEntity> findByBorrowedUserUsernameAndDeletedFalse(String username);

    boolean existsByBookNameAndDeletedFalse(String bookName);
}
