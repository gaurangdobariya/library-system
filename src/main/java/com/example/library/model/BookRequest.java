package com.example.library.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @Min(value = 0, message = "Price must be non-negative")
        @NotNull(message = "Price is mandatory")
        double price) {}
