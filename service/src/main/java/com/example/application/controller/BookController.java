package com.example.application.controller;

import com.example.application.dto.response.BookResponse;
import com.example.application.model.CustomUser;
import com.example.application.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final IBookService bookService;

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/add")
    public List<BookResponse> addBook(@RequestParam String bookName, Authentication authentication){
        String userId = getUserName(authentication);
        System.out.println("bookName = " + bookName);
        System.out.println("userId = " + userId);
        return bookService.add(userId, bookName);
    }

    @GetMapping("/list")
    public List<BookResponse> list( Authentication authentication){
        String userId = getUserName(authentication);
        return bookService.list(userId);
    }

    @GetMapping("/borrow")
    public BookResponse borrowBook(@RequestParam String bookName, Authentication authentication){
        String userId = getUserName(authentication);
        System.out.println("bookName = " + bookName);
        System.out.println("userId = " + userId);
        return bookService.borrow(userId, bookName);
    }

    private String getUserName(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if(principal instanceof String username){
            return username;
        } else if(principal instanceof CustomUser customUser){
            return customUser.getUsername();
        }
        return null;
    }

    @GetMapping("/return")
    public BookResponse returnBook(@RequestParam String bookName, Authentication authentication){
        String userId = getUserName(authentication);
        System.out.println("bookName = " + bookName);
        System.out.println("userId = " + userId);
        return bookService.returnBook(userId, bookName);
    }

    @GetMapping("/wait-list")
    public BookResponse waitList(@RequestParam String bookName, Authentication authentication){
        String userId = getUserName(authentication);
        System.out.println("bookName = " + bookName);
        System.out.println("userId = " + userId);
        return bookService.waitList(userId, bookName);
    }
}
