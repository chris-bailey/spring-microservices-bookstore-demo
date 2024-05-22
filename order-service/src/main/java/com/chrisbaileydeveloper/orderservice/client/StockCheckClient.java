package com.chrisbaileydeveloper.orderservice.client;

import com.chrisbaileydeveloper.orderservice.dto.StockCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "stock-check-service")
public interface StockCheckClient {

    @GetMapping("/api/stockcheck")
    List<StockCheckResponse> isInStock(@RequestParam List<String> skuCode);
}
