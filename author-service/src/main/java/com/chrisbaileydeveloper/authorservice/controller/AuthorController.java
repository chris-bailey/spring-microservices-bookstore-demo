package com.chrisbaileydeveloper.authorservice.controller;

import com.chrisbaileydeveloper.authorservice.dto.AuthorRequest;
import com.chrisbaileydeveloper.authorservice.dto.AuthorResponse;
import com.chrisbaileydeveloper.authorservice.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest) {
        return authorService.createAuthor(authorRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<AuthorResponse> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAuthor(@PathVariable String id) {
        return authorService.deleteAuthor(Long.parseLong(id))
                .map(isDeleted -> isDeleted ? ResponseEntity.ok().build() : ResponseEntity.noContent().build());
    }
}
