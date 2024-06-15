package com.chrisbaileydeveloper.stockcheckservice.util;

import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck;
import com.chrisbaileydeveloper.stockcheckservice.repository.StockCheckRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final StockCheckRepository stockCheckRepository;
    @Override
    public void run(String... args) throws Exception {
        StockCheck stockCheck = new StockCheck();
        stockCheck.setSkuCode("design_patterns_gof");
        stockCheck.setQuantity(100);

        StockCheck stockCheck1 = new StockCheck();
        stockCheck1.setSkuCode("mythical_man_month");
        stockCheck1.setQuantity(0);

        stockCheckRepository.save(stockCheck);
        stockCheckRepository.save(stockCheck1);
    }
}
