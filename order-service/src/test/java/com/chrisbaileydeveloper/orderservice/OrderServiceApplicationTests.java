package com.chrisbaileydeveloper.orderservice;

import com.chrisbaileydeveloper.orderservice.dto.OrderRequest;
import com.chrisbaileydeveloper.orderservice.dto.OrderLineItemsDto;
import com.chrisbaileydeveloper.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
public class OrderServiceApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("order_service")
			.withUsername("admin")
			.withPassword("password")
	    	.withInitScript("schema.sql");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private OrderRepository orderRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresDBContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresDBContainer::getUsername);
		registry.add("spring.datasource.password", postgresDBContainer::getPassword);
	}

	@BeforeEach
	void setup() {
		orderRepository.deleteAll(); // Clean up the database before each test
	}

	@Test
	void shouldPlaceOrder() throws Exception {
		OrderLineItemsDto orderLineItemsDto = new OrderLineItemsDto();
		orderLineItemsDto.setSkuCode("design_patterns_gof");
		orderLineItemsDto.setPrice(BigDecimal.valueOf(29));
		orderLineItemsDto.setQuantity(1);

		OrderRequest orderRequest = new OrderRequest();
		orderRequest.setOrderLineItemsDtoList(Collections.singletonList(orderLineItemsDto));

		String orderRequestString = objectMapper.writeValueAsString(orderRequest);

		mockMvc.perform(post("/api/order")
						.contentType(MediaType.APPLICATION_JSON)
						.content(orderRequestString))
				.andExpect(status().isCreated());
	}
}
