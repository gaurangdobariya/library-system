package com.example.library.model;

import com.example.library.entity.Book;

public record BookDto(
        Long id,
        String title,
        String author,
        double price) {

    public BookDto(Book book) {
        this(book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice());
    }
}
