package com.chrisbaileydeveloper.stockcheckservice.dto

data class StockCheckResponse(
    val skuCode: String,
    val isInStock: Boolean
)
