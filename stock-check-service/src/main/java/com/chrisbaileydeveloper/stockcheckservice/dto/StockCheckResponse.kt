package com.chrisbaileydeveloper.stockcheckservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockCheckResponse {
    private String skuCode;
    private boolean isInStock;
}
