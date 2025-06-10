package com.example.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private Double price;
    private LocalDateTime creationDate;

    public Book(String title, String author, Double price) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.creationDate = LocalDateTime.now();
    }

    public Book(Long id,
                String title,
                String author,
                Double price,
                LocalDateTime creationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.creationDate = creationDate;
    }
}