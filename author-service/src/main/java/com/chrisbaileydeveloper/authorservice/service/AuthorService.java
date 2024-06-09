package com.chrisbaileydeveloper.authorservice.service;

import com.chrisbaileydeveloper.authorservice.dto.AuthorRequest;
import com.chrisbaileydeveloper.authorservice.dto.AuthorResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {
    Mono<AuthorResponse> createAuthor(AuthorRequest authorRequest);
    Flux<AuthorResponse> getAllAuthors();
    Mono<Boolean> deleteAuthor(Long id);
}
