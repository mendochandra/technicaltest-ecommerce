package com.techtest.product_service.service;


import com.techtest.product_service.dto.ProductResponse;
import com.techtest.product_service.model.Product;

public interface ProductService {

    ProductResponse save(ProductResponse productResponse);
}
