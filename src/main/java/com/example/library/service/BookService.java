package com.example.library.service;

import com.example.library.model.BookDto;
import com.example.library.model.BookRequest;

import java.util.List;

public interface BookService {

    public List<BookDto> getAllBooks();

    public BookDto getBookById(Long id);

    public BookDto addBook(BookRequest request);

    public BookDto updateBook(Long id, BookRequest request);

    public void deleteBook(Long id);
}
