package com.chrisbaileydeveloper.stockcheckservice.controller;

import com.chrisbaileydeveloper.stockcheckservice.dto.StockCheckResponse;
import com.chrisbaileydeveloper.stockcheckservice.service.StockCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stockcheck")
@RequiredArgsConstructor
@Slf4j
public class StockCheckController {

    private final StockCheckService stockCheckService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockCheckResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Stock check request received for skuCode: {}", skuCode);
        return stockCheckService.isInStock(skuCode);
    }
}

