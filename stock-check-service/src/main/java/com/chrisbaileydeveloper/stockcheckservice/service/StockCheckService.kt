package com.chrisbaileydeveloper.stockcheckservice.service

import com.chrisbaileydeveloper.stockcheckservice.dto.StockCheckResponse
import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StockCheckService(private val stockCheckRepository: StockCheckRepository) {

    private val log = LoggerFactory.getLogger(StockCheckService::class.java)

    @Transactional(readOnly = true)
    open fun isInStock(skuCode: List<String>): List<StockCheckResponse> {
        log.info("Checking Stock for {}", skuCode)
        return stockCheckRepository.findBySkuCodeIn(skuCode).map { stockCheck ->
            StockCheckResponse(
                skuCode = stockCheck.skuCode,
                isInStock = stockCheck.quantity > 0
            )
        }
    }
}
