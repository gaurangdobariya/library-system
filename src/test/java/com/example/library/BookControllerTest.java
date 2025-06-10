package com.example.library;

import com.example.library.controller.BookController;
import com.example.library.model.BookDto;
import com.example.library.model.BookRequest;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private BookService bookService;

	@Autowired
	private ObjectMapper objectMapper;

	private BookDto bookDto;
	private BookRequest bookRequest;

	@BeforeEach
	void setUp() {
		bookDto = new BookDto(1L, "RichDad", "xyz", 10.0);
		bookRequest = new BookRequest(bookDto.title(), bookDto.author(),bookDto.price());
	}

	@Test
	void whenAddBook_thenReturns200AndBookDto() throws Exception {
		when(bookService.addBook(any(BookRequest.class))).thenReturn(bookDto);

		mockMvc.perform(post("/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(bookRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(bookDto.id().intValue())))
				.andExpect(jsonPath("$.title", is(bookDto.title())))
				.andExpect(jsonPath("$.author", is(bookDto.author())))
				.andExpect(jsonPath("$.price", is(bookDto.price())));
	}

	@Test
	void whenGetAllBooks_thenReturns200AndListOfBooks() throws Exception {
		List<BookDto> allBooks = Collections.singletonList(bookDto);
		when(bookService.getAllBooks()).thenReturn(allBooks);

		mockMvc.perform(get("/books"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(bookDto.id().intValue())))
				.andExpect(jsonPath("$[0].title", is(bookDto.title())))
				.andExpect(jsonPath("$[0].author", is(bookDto.author())))
				.andExpect(jsonPath("$[0].price", is(bookDto.price())));
	}

	@Test
	void whenGetBookById_withValidId_thenReturns200AndBookDto() throws Exception {
		when(bookService.getBookById(1L)).thenReturn(bookDto);

		mockMvc.perform(get("/books/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(bookDto.id().intValue())))
				.andExpect(jsonPath("$.title", is(bookDto.title())))
				.andExpect(jsonPath("$.author", is(bookDto.author())))
				.andExpect(jsonPath("$.price", is(bookDto.price())));
	}

}
