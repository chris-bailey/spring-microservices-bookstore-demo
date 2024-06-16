package com.chrisbaileydeveloper.stockcheckservice;
//
//import com.chrisbaileydeveloper.stockcheckservice.dto.StockCheckResponse;
//import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck;
//import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository;
//import com.chrisbaileydeveloper.stockcheckservice.service.StockCheckService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.fail;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Testcontainers
public class StockCheckServiceApplicationTests {
//
//	@Container
//	static PostgreSQLContainer<?> postgresDBContainer = new PostgreSQLContainer<>("postgres:latest")
//			.withDatabaseName("stock-check-service")
//			.withUsername("admin")
//			.withPassword("password")
//			.withInitScript("schema.sql");
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@Autowired
//	private StockCheckRepository stockCheckRepository;
//
//	@Autowired
//	private StockCheckService stockCheckService;
//
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry registry) {
//		registry.add("spring.datasource.url", postgresDBContainer::getJdbcUrl);
//		registry.add("spring.datasource.username", postgresDBContainer::getUsername);
//		registry.add("spring.datasource.password", postgresDBContainer::getPassword);
//	}
//
//	@BeforeEach
//	void setup() {
//		stockCheckRepository.deleteAll(); // Clean up the database before each test
//		// Add test data
//		StockCheck stockCheck1 = new StockCheck(null, "design_patterns_gof", 100);
//		StockCheck stockCheck2 = new StockCheck(null, "mythical_man_month", 0);
//		stockCheckRepository.saveAll(List.of(stockCheck1, stockCheck2));
//	}
//
//	@Test
//	void shouldCheckStock() throws Exception {
//		String skuCodes = "design_patterns_gof,mythical_man_month";
//
//		mockMvc.perform(get("/api/stockcheck")
//						.param("skuCode", skuCodes)
//						.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk());
//	}
//
//	@Test
//	void shouldReturnCorrectStockStatus() {
//		List<StockCheckResponse> responses = stockCheckService.isInStock(List.of("design_patterns_gof", "mythical_man_month"));
//
//		assertThat(responses).hasSize(2);
//
//		responses.forEach(response -> {
//			if (response.getSkuCode().equals("design_patterns_gof")) {
//				assertThat(response.isInStock()).isTrue();
//			} else if (response.getSkuCode().equals("mythical_man_month")) {
//				assertThat(response.isInStock()).isFalse();
//			} else {
//				fail("Unexpected SKU code: " + response.getSkuCode());
//			}
//		});
//	}
}
