package com.chrisbaileydeveloper.authorservice.util;

import com.chrisbaileydeveloper.authorservice.model.Author;
import com.chrisbaileydeveloper.authorservice.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Override
    public void run(String... args) throws Exception {
        authorRepository.count()
                .filter(count -> count == 0)
                .flatMapMany(count -> {
                    Author author1 = new Author(null, "Erich Gamma", LocalDate.of(1961, 3, 13));
                    Author author2 = new Author(null, "Andrew Hunt", LocalDate.of(1964, 6, 11));
                    Author author3 = new Author(null, "Robert C. Martin", LocalDate.of(1952, 12, 5));
                    Author author4 = new Author(null, "Martin Fowler", LocalDate.of(1963, 12, 18));
                    Author author5 = new Author(null, "Steve McConnell", LocalDate.of(1962, 1, 26));
                    return authorRepository.saveAll(Flux.just(author1, author2, author3, author4, author5));
                })
                .subscribe();
    }
}
