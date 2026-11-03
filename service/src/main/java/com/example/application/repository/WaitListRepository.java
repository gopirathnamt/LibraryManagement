package com.example.application.repository;

import com.example.application.entity.WaitListEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WaitListRepository extends JpaRepository<WaitListEntity, Long> {

    List<WaitListEntity> findByUserUsernameAndDeletedFalse(String username);

    @Query("select w.bookEntity.bookName from WaitListEntity w where w.user.username=:username and w.deleted = false ")
    List<String> findBooksByUserUsername(String username);

    Optional<WaitListEntity> findTopByBookEntityBookNameAndDeletedFalse(String bookName, Sort sort);
}