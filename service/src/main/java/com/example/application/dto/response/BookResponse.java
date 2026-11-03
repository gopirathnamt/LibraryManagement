package com.example.application.dto.response;

import com.example.application.entity.BookEntity;

import java.util.List;

public record BookResponse(String name, boolean borrowed, boolean waitlist, boolean available) {

    public static BookResponse build(BookEntity bookEntity, String userName, List<String> waitListEntities) {
        return new BookResponse(bookEntity.getBookName(), userName.equals(bookEntity.getBorrowedUserName()), waitListEntities.contains(bookEntity.getBookName()), !bookEntity.isBorrowed());
    }
}
