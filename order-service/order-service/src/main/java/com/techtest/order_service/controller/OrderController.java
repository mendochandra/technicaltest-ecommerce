package com.techtest.order_service.controller;

import com.techtest.order_service.dto.OrderRequest;
import com.techtest.order_service.service.OrderService;
import com.techtest.order_service.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest){
        orderServiceImpl.placeOrder(orderRequest);
        return ResponseEntity.ok().build();
    }


}
