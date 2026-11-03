package com.example.application.service;

import com.example.application.dto.response.BookResponse;

import java.util.List;

public interface IBookService {
    List<BookResponse> add(String userId, String bookName);

    BookResponse borrow(String userId, String bookName);

    BookResponse returnBook(String userId, String bookName);

    BookResponse waitList(String userId, String bookName);

    List<BookResponse> list(String userId);
}
