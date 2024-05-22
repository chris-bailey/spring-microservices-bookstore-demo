package com.chrisbaileydeveloper.orderservice.service;

import com.chrisbaileydeveloper.orderservice.client.StockCheckClient;
import com.chrisbaileydeveloper.orderservice.model.OrderLineItems;
import com.chrisbaileydeveloper.orderservice.dto.StockCheckResponse;
import com.chrisbaileydeveloper.orderservice.dto.OrderLineItemsDto;
import com.chrisbaileydeveloper.orderservice.dto.OrderRequest;
import com.chrisbaileydeveloper.orderservice.event.OrderPlacedEvent;
import com.chrisbaileydeveloper.orderservice.model.Order;
import com.chrisbaileydeveloper.orderservice.repository.OrderRepository;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final StockCheckClient stockCheckClient;
    private final ObservationRegistry observationRegistry;
    private final ApplicationEventPublisher applicationEventPublisher;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Stock Check Service, and place order if book is in stock
        Observation stockCheckServiceObservation = Observation.createNotStarted("stock-check-service-lookup",
                this.observationRegistry);
        stockCheckServiceObservation.lowCardinalityKeyValue("call", "stock-check-service");
        return stockCheckServiceObservation.observe(() -> {
            List<StockCheckResponse> stockCheckResponses = stockCheckClient.isInStock(skuCodes);

            boolean allBooksInStock = stockCheckResponses.stream()
                    .allMatch(StockCheckResponse::isInStock);

            if (allBooksInStock) {
                orderRepository.save(order);
                // publish the Order Placed Event to Kafka
                applicationEventPublisher.publishEvent(new OrderPlacedEvent(this, order.getOrderNumber()));
                return "Order Placed";
            } else {
                throw new IllegalArgumentException("The book is not in stock. Please try again later.");
            }
        });

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
