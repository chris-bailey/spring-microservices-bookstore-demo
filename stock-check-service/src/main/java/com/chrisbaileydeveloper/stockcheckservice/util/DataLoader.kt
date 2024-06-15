package com.chrisbaileydeveloper.stockcheckservice.util

import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck
import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(private val stockCheckRepository: StockCheckRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
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
}
