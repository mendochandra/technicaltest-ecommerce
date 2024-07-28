package com.techtest.product_service.service.impl;


import com.techtest.product_service.dto.ProductResponse;
import com.techtest.product_service.model.Product;
import com.techtest.product_service.repository.ProductRepository;
import com.techtest.product_service.service.ProductGetService;
import com.techtest.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final WebClient webClient;

    @Override
    public ProductResponse save(ProductResponse productResponse) {
        Product product = new Product();
        product.setPrice(productResponse.getPrice());
        product.setName(productResponse.getName());
        product.setDescription(productResponse.getDescription());
        productRepository.save(product);

        webClient.post()
                .uri("http://localhost:8082/api/inventory/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(productResponse), ProductResponse.class)
                .retrieve()
                .bodyToMono(ProductResponse.class)
                .block();

        return productResponse;
    }
}
