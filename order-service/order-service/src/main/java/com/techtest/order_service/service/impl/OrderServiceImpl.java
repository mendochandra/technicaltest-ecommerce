package com.techtest.order_service.service.impl;

import com.techtest.order_service.dto.InventoryResponse;
import com.techtest.order_service.dto.OrderLineItemsDto;
import com.techtest.order_service.dto.OrderRequest;
import com.techtest.order_service.model.Order;
import com.techtest.order_service.model.OrderLineItems;
import com.techtest.order_service.repository.OrderRepository;
import com.techtest.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public String placeOrder(OrderRequest orderRequest){
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

        //call inventory service, and place order if prodt is in
        //stock
        InventoryResponse[] inventoryResponsArray = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean AllProductInStock = Arrays.stream(inventoryResponsArray).allMatch(InventoryResponse::isInStock);

        if (AllProductInStock){
            orderRepository.save(order);
            webClient.put()
                    .uri("http://localhost:8082/api/inventory/update/stock")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderRequest), OrderRequest.class)
                    .retrieve()
                    .bodyToMono(OrderRequest.class)
                    .block();
            return "success order";
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
