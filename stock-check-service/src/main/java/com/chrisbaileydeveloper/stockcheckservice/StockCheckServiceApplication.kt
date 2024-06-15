package com.chrisbaileydeveloper.stockcheckservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class StockCheckServiceApplication

fun main(args: Array<String>) {
    runApplication<StockCheckServiceApplication>(*args)
}
