package com.chrisbaileydeveloper.bookservice.repository;

import com.chrisbaileydeveloper.bookservice.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String> {
}
