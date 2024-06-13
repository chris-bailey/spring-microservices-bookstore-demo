package com.chrisbaileydeveloper.bookservice;

import com.chrisbaileydeveloper.bookservice.dto.BookRequest;
import com.chrisbaileydeveloper.bookservice.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class BookServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.2"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    static {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeEach
    void setup() {
        bookRepository.deleteAll(); // Ensure the database is clean before each test
    }

    @Test
    void shouldCreateBook() throws Exception {
        BookRequest bookRequest = getBookRequest();

        String mutation = String.format(
                "mutation { createBook(bookRequest: { name: \"%s\", description: \"%s\", price: %s }) { id name description price } }",
                bookRequest.getName(),
                bookRequest.getDescription(),
                bookRequest.getPrice()
        );

        String graphqlRequest = String.format("{\"query\": \"%s\"}", mutation.replace("\"", "\\\""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/graphql")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(graphqlRequest))
                .andExpect(status().isOk());

        Assertions.assertEquals(1, bookRepository.findAll().size());
    }

    private BookRequest getBookRequest() {
        return BookRequest.builder()
                .name("Design Patterns: Elements of Reusable Object-Oriented Software")
                .description("A classic book on software design patterns for object-oriented programming.")
                .price(29.0)
                .build();
    }
}
