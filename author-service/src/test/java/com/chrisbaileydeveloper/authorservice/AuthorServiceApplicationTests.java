package com.chrisbaileydeveloper.authorservice;

import com.chrisbaileydeveloper.authorservice.model.Author;
import com.chrisbaileydeveloper.authorservice.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class AuthorServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("author_service")
			.withUsername("admin")
			.withPassword("password");

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private AuthorRepository authorRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.r2dbc.url", () -> String.format("r2dbc:postgresql://%s:%d/%s",
				postgresDBContainer.getHost(),
				postgresDBContainer.getFirstMappedPort(),
				postgresDBContainer.getDatabaseName()));
		registry.add("spring.r2dbc.username", postgresDBContainer::getUsername);
		registry.add("spring.r2dbc.password", postgresDBContainer::getPassword);
	}

	@BeforeEach
	void setUp() {
		// Clear the database before each test
		authorRepository.deleteAll().block();
	}

	@Test
	void testCreateAuthor() {
		Author author = new Author(null, "Author Name", LocalDate.of(1980, 1, 1));
		webTestClient.post().uri("/api/authors")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(author)
				.exchange()
				.expectStatus().isCreated()
				.expectBody()
				.jsonPath("$.name").isEqualTo("Author Name");
	}

	@Test
	void testGetAllAuthors() {
		// Pre-populate the database
		authorRepository.save(new Author(null, "Author Name", LocalDate.of(1980, 1, 1))).block();

		webTestClient.get().uri("/api/authors")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(Author.class)
				.hasSize(1);
	}

	@Test
	void testDeleteExistingAuthor() {
		// First, save an author to the database
		Author savedAuthor = authorRepository.save(new Author(null, "Author to Delete", LocalDate.of(1980, 1, 1))).block();

		// Now, attempt to delete the author
		webTestClient.delete().uri("/api/authors/" + savedAuthor.getId())
				.exchange()
				.expectStatus().isOk()
				.expectBody().isEmpty(); // Confirm the response body is empty

		// Confirm that the author is actually deleted from the repository
		assertEquals(0, authorRepository.count().block());
	}

	@Test
	void testDeleteNonExistingAuthor() {
		// Attempt to delete a non-existing author
		webTestClient.delete().uri("/api/authors/99999") // Assuming 99999 is an ID that does not exist
				.exchange()
				.expectStatus().isNoContent()
				.expectBody().isEmpty(); // Confirm the response body is empty

		// The database should still be empty
		assertEquals(0, authorRepository.count().block());
	}
}
