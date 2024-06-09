package com.chrisbaileydeveloper.authorservice.service;

import com.chrisbaileydeveloper.authorservice.dto.AuthorRequest;
import com.chrisbaileydeveloper.authorservice.dto.AuthorResponse;
import com.chrisbaileydeveloper.authorservice.model.Author;
import com.chrisbaileydeveloper.authorservice.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Mono<AuthorResponse> createAuthor(AuthorRequest authorRequest) {
        Author author = Author.builder()
                .name(authorRequest.getName())
                .birthDate(authorRequest.getBirthDate())
                .build();

        return authorRepository.save(author)
                .map(this::mapToAuthorResponse)
                .doOnSuccess(a -> log.info("Author has been saved with id: {}", a.getId()));
    }

    @Override
    public Flux<AuthorResponse> getAllAuthors() {
        return authorRepository.findAll()
                .map(this::mapToAuthorResponse);
    }

    @Override
    public Mono<Boolean> deleteAuthor(Long id) {
        return authorRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return authorRepository.deleteById(id)
                                .thenReturn(true)
                                .doOnSuccess(a -> log.info("Author has been deleted with id: {}", id));
                    } else {
                        log.info("No author found with id: {}", id);
                        return Mono.just(false);
                    }
                });
    }

    private AuthorResponse mapToAuthorResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId().toString())
                .name(author.getName())
                .birthDate(author.getBirthDate())
                .build();
    }
}
