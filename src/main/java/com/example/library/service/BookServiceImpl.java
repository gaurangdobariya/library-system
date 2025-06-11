package com.example.library.service;

import com.example.library.model.BookRequest;
import com.example.library.entity.Book;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.BookDto;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.logging.Logger;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements  BookService{

    private static final Logger LOGGER = Logger.getLogger(BookServiceImpl.class.getName());

    private final BookRepository bookRepository;

    @Value("${APP_ENV:QA}")
    private String appEnv;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBooks() {

        LOGGER.info("getAllBooks : " + appEnv);
        return bookRepository.findAll()
                .stream()
                .map(BookDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        LOGGER.info("getBookById");

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found."));
        return new BookDto(book);
    }

    @Transactional
    public BookDto addBook(BookRequest request) {
        LOGGER.info("addBook");

        Book book = new Book(request.title(), request.author(), request.price());
        return new BookDto(bookRepository.save(book));
    }

    @Transactional
    public BookDto updateBook(Long id, BookRequest request) {
        LOGGER.info("updateBook");

        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found."));


        existingBook.setTitle(request.title());
        existingBook.setAuthor(request.author());
        existingBook.setPrice(request.price());

        Book updatedBook = bookRepository.save(existingBook);

        return new BookDto(updatedBook);
    }

    @Transactional
    public void deleteBook(Long id) {

        LOGGER.info("updateBook");
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }
}