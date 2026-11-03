package com.example.application.service.impl;

import com.example.application.dto.response.BookResponse;
import com.example.application.entity.BookEntity;
import com.example.application.entity.UserEntity;
import com.example.application.entity.WaitListEntity;
import com.example.application.exception.BookException;
import com.example.application.exception.UserException;
import com.example.application.repository.BookRepository;
import com.example.application.repository.UserRepository;
import com.example.application.repository.WaitListRepository;
import com.example.application.service.IBookService;
import com.example.application.value.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IBookService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final WaitListRepository waitListRepository;

    @Autowired
    public BookService(UserRepository userRepository, BookRepository bookRepository, WaitListRepository waitListRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.waitListRepository = waitListRepository;
    }

    @Override
    public List<BookResponse> add(String userId, String bookName) {
        UserEntity userEntity = userRepository.findByUsernameAndDeletedFalse(userId).filter(user -> {
            System.out.println("user.getUsername() = " + user.getUsername());
            System.out.println("user.getUserType() = " + user.getUserType());
            return user.getUserType() == UserType.ADMIN;
        }).orElseThrow(() -> new UserException("User is not allowed to add books"));
        if (bookRepository.existsByBookNameAndDeletedFalse(bookName)) {
            return null;
        }
        BookEntity bookEntity = new BookEntity();
        bookEntity.setBookName(bookName);
        bookEntity.setCreatedBy(userEntity.getUsername());
        bookRepository.saveAndFlush(bookEntity);
        return list(userId);
    }

    @Override
    public BookResponse borrow(String userId, String bookName) {
        UserEntity userEntity = userRepository.findByUsernameAndDeletedFalse(userId)
                .orElseThrow(() -> new UserException("User is not allowed to borrow books"));
        BookEntity bookEntity = bookRepository.findByBookNameAndDeletedFalse(bookName)
                .orElseThrow(() -> new BookException("Provided book is not available in the library"));
        if (bookEntity.isBorrowed()) {
            throw new BookException("Book is already borrowed, You can add the book to wait list using wait list option");
        }
        bookEntity.setBorrowed(true);
        bookEntity.setBorrowedUser(userEntity);
        bookEntity.setBorrowedUserName(userId);
        bookRepository.saveAndFlush(bookEntity);
        return BookResponse.build(bookEntity, userId, List.of());
    }

    @Override
    public BookResponse returnBook(String userId, String bookName) {
        BookEntity bookEntity = bookRepository.findByBookNameAndBorrowedUserUsernameAndDeletedFalse(bookName, userId)
                .orElseThrow(() -> new BookException("Book wasn't borrowed by the user to return"));
        bookEntity.setBorrowed(false);
        bookEntity.setBorrowedUser(null);
        bookEntity.setBorrowedUserName(null);
        waitListRepository.findTopByBookEntityBookNameAndDeletedFalse(bookName, Sort.by(WaitListEntity::getId)
                .ascending()).ifPresent(
                waitListEntity -> {
                    System.out.println("waitListEntity = " + waitListEntity);
                    bookEntity.setBorrowed(true);
                    bookEntity.setBorrowedUser(waitListEntity.getUser());
                    bookEntity.setBorrowedUserName(waitListEntity.getUser().getUsername());
                    waitListEntity.setDeleted(true);
                    waitListRepository.saveAndFlush(waitListEntity);
                }
        );
        bookRepository.saveAndFlush(bookEntity);
        return BookResponse.build(bookEntity, userId, List.of());

    }

    @Override
    public BookResponse waitList(String userId, String bookName) {
        UserEntity userEntity = userRepository.findByUsernameAndDeletedFalse(userId)
                .orElseThrow(() -> new UserException("User is not allowed to wait list books"));
        BookEntity bookEntity = bookRepository.findByBookNameAndDeletedFalse(bookName)
                .orElseThrow(() -> new BookException("Provided book is not available in the library"));
        WaitListEntity waitListEntity = new WaitListEntity();
        waitListEntity.setBookEntity(bookEntity);
        waitListEntity.setUser(userEntity);
        waitListRepository.saveAndFlush(waitListEntity);
        return BookResponse.build(bookEntity, userId, List.of(bookName));
    }

    @Override
    public List<BookResponse> list(String userId) {
        List<BookEntity> books = bookRepository.findAll();
        List<String> waitListEntities = waitListRepository.findBooksByUserUsername(userId);
        return books.stream()
                .map(bookEntity -> BookResponse.build(bookEntity, userId, waitListEntities))
                .toList();
    }
}
