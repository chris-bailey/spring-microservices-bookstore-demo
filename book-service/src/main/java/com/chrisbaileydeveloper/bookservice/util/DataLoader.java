package com.chrisbaileydeveloper.bookservice.util;

import com.chrisbaileydeveloper.bookservice.model.Book;
import com.chrisbaileydeveloper.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if the repository is empty and load initial data if needed
        if (bookRepository.count() < 1) {
            Book book = new Book();
            book.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
            book.setDescription("A classic book on software design patterns for object-oriented programming.");
            book.setPrice(BigDecimal.valueOf(29));

            bookRepository.save(book);
        }
    }
}
