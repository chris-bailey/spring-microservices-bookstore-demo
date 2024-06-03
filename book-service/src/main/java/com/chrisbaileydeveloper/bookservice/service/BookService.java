package com.chrisbaileydeveloper.bookservice.service;

import com.chrisbaileydeveloper.bookservice.dto.BookRequest;
import com.chrisbaileydeveloper.bookservice.dto.BookResponse;
import com.chrisbaileydeveloper.bookservice.model.Book;
import com.chrisbaileydeveloper.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public void createBook(BookRequest bookRequest) {
        Book book = Book.builder()
                .name(bookRequest.getName())
                .description(bookRequest.getDescription())
                .price(bookRequest.getPrice())
                .build();

        bookRepository.save(book);
        log.info("Book has been saved with id: {}", book.getId());
    }

    public List<BookResponse> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(this::mapToBookResponse).toList();
    }

    public boolean deleteBook(String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            log.info("Book has been deleted with id: {}", id);
            return true;
        } else {
            log.info("No book found with id: {}", id);
            return false;
        }
    }

    private BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .price(book.getPrice())
                .build();
    }
}
