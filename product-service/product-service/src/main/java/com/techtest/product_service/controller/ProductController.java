package com.techtest.product_service.controller;


import com.techtest.product_service.dto.ProductResponse;
import com.techtest.product_service.model.Product;
import com.techtest.product_service.service.ProductGetService;
import com.techtest.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductGetService productGetService;

    @GetMapping("/get-all")
    public List<Product>getProducts() {
        return productGetService.getAllProducts();
    }

    @PostMapping("/save")
    public ProductResponse saveProduct(@RequestBody ProductResponse product){
        return productService.save(product);
    }
}
