package com.techtest.order_service.service;

import com.techtest.order_service.dto.OrderRequest;


public interface OrderService {

    String placeOrder(OrderRequest orderRequest);

}
