package com.chrisbaileydeveloper.stockcheckservice.controller

import com.chrisbaileydeveloper.stockcheckservice.dto.StockCheckResponse
import com.chrisbaileydeveloper.stockcheckservice.service.StockCheckService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stockcheck")
class StockCheckController(private val stockCheckService: StockCheckService) {

    private val log = LoggerFactory.getLogger(StockCheckController::class.java)

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun isInStock(@RequestParam skuCode: List<String>): List<StockCheckResponse> {
        log.info("Stock check request received for skuCode: {}", skuCode)
        return stockCheckService.isInStock(skuCode)
    }
}
