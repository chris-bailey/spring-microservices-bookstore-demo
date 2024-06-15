package com.chrisbaileydeveloper.stockcheckservice.service;

import com.chrisbaileydeveloper.stockcheckservice.dto.StockCheckResponse;
import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockCheckService {

    private final StockCheckRepository stockCheckRepository;

    @Transactional(readOnly = true)
    @SneakyThrows
    public List<StockCheckResponse> isInStock(List<String> skuCode) {
        log.info("Checking Stock for {}", skuCode);
        return stockCheckRepository.findBySkuCodeIn(skuCode).stream()
                .map(stockCheck ->
                        StockCheckResponse.builder()
                                .skuCode(stockCheck.getSkuCode())
                                .isInStock(stockCheck.getQuantity() > 0)
                                .build()
                ).toList();
    }
}
