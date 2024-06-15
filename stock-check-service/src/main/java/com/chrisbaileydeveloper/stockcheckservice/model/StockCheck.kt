package com.chrisbaileydeveloper.stockcheckservice.model

import jakarta.persistence.*

@Entity
@Table
data class StockCheck(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val skuCode: String = "",
    val quantity: Int = 0
) {
    constructor() : this(0L, "", 0)
}
