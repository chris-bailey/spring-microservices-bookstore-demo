package com.chrisbaileydeveloper.authorservice.repository;

import com.chrisbaileydeveloper.authorservice.model.Author;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends ReactiveCrudRepository<Author, Long> {
}
