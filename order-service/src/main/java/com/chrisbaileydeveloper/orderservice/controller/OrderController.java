package com.chrisbaileydeveloper.orderservice.controller;

import com.chrisbaileydeveloper.orderservice.service.OrderService;
import com.chrisbaileydeveloper.orderservice.dto.OrderRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "stock-check", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "stock-check")
    @Retry(name = "stock-check")
    public CompletableFuture<ResponseEntity<Map<String, String>>> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing the Order");
        return CompletableFuture.supplyAsync(() -> {
            try {
                Map<String, String> result = orderService.placeOrder(orderRequest);
                HttpStatus status = "success".equals(result.get("status")) ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR;
                return ResponseEntity.status(status).body(result);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "status", "error",
                        "message", e.getMessage()
                ));
            }
        });
    }

    public CompletableFuture<ResponseEntity<Map<String, String>>> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
        log.info("Unable to place Order - Fallback method being executed");
        return CompletableFuture.supplyAsync(() -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Map.of(
                "status", "error",
                "message", "The order service is busy or the item is out of stock."
        )));
    }
}
