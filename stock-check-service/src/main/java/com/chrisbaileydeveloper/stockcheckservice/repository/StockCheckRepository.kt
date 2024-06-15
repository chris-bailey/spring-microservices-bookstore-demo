package com.chrisbaileydeveloper.stockcheckservice.repository

import com.chrisbaileydeveloper.stockcheckservice.model.StockCheck
import org.springframework.data.jpa.repository.JpaRepository

interface StockCheckRepository : JpaRepository<StockCheck, Long> {
    fun findBySkuCodeIn(skuCode: List<String>): List<StockCheck>
}
