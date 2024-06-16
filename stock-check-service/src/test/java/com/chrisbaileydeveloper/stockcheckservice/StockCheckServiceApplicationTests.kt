package com.chrisbaileydeveloper.stockcheckservice

import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck
import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository
import com.chrisbaileydeveloper.stockcheckservice.service.StockCheckService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.fail
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class StockCheckServiceApplicationTests {

    companion object {
        @Container
        val postgresDBContainer = PostgreSQLContainer<Nothing>("postgres:latest").apply {
            withDatabaseName("stock-check-service")
            withUsername("admin")
            withPassword("password")
            withInitScript("schema.sql")
        }

        @JvmStatic
        @DynamicPropertySource
        fun setProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresDBContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDBContainer::getUsername)
            registry.add("spring.datasource.password", postgresDBContainer::getPassword)
        }
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var stockCheckRepository: StockCheckRepository

    @Autowired
    private lateinit var stockCheckService: StockCheckService

    @BeforeEach
    fun setup() {
        stockCheckRepository.deleteAll() // Clean up the database before each test

        // Add test data
        val designPatterns = StockCheck(
            skuCode = "design_patterns_gof",
            quantity = 100
        )

        val mythicalMan = StockCheck(
            skuCode = "mythical_man_month",
            quantity = 0
        )

        stockCheckRepository.save(designPatterns)
        stockCheckRepository.save(mythicalMan)
    }

    @Test
    @Throws(Exception::class)
    fun shouldCheckStock() {
        val skuCodes = "design_patterns_gof,mythical_man_month"

        mockMvc.perform(get("/api/stockcheck")
            .param("skuCode", skuCodes)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
    }

    @Test
    fun shouldReturnCorrectStockStatus() {
        val responses = stockCheckService.isInStock(listOf("design_patterns_gof", "mythical_man_month"))

        assertThat(responses).hasSize(2)

        responses.forEach { response ->
            when (response.skuCode) {
                "design_patterns_gof" -> assertThat(response.isInStock).isTrue
                "mythical_man_month" -> assertThat(response.isInStock).isFalse
                else -> fail("Unexpected SKU code: ${response.skuCode}")
            }
        }
    }
}
