package com.chrisbaileydeveloper.stockcheckservice.repository;

import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockCheckRepository extends JpaRepository<StockCheck, Long> {
    List<StockCheck> findBySkuCodeIn(List<String> skuCode);
}
