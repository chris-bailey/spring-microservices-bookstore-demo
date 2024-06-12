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
            Book book1 = new Book();
            book1.setName("Design Patterns: Elements of Reusable Object-Oriented Software");
            book1.setDescription("A classic book on software design patterns for object-oriented programming.");
            book1.setPrice(29.0);

            Book book2 = new Book();
            book2.setName("The Pragmatic Programmer: Your Journey to Mastery");
            book2.setDescription("A comprehensive guide to the art of computer programming and best practices.");
            book2.setPrice(42.0);

            Book book3 = new Book();
            book3.setName("Clean Code: A Handbook of Agile Software Craftsmanship");
            book3.setDescription("A book focused on writing clean, maintainable code.");
            book3.setPrice(35.0);

            Book book4 = new Book();
            book4.setName("Refactoring: Improving the Design of Existing Code");
            book4.setDescription("A guide to refactoring and improving existing code.");
            book4.setPrice(40.0);

            Book book5 = new Book();
            book5.setName("Code Complete: A Practical Handbook of Software Construction");
            book5.setDescription("A comprehensive guide to software construction and best practices.");
            book5.setPrice(45.0);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
            bookRepository.save(book4);
            bookRepository.save(book5);
        }
    }
}
