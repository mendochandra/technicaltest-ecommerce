package com.techtest.product_service.service.impl;

import com.techtest.product_service.model.Product;
import com.techtest.product_service.repository.ProductRepository;
import com.techtest.product_service.service.ProductGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductGetServiceImpl implements ProductGetService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
