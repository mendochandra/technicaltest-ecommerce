package com.techtest.product_service;


import com.techtest.product_service.dto.ProductResponse;
import com.techtest.product_service.model.Product;
import com.techtest.product_service.repository.ProductRepository;
import com.techtest.product_service.service.impl.ProductGetServiceImpl;
import com.techtest.product_service.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;



import java.math.BigDecimal;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ProductServiceApplicationTests {

	@InjectMocks
	private ProductServiceImpl productServiceImpl;

	@Mock
	private ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void testSaveUser() {
		ProductResponse productResponse = new ProductResponse();
		productResponse.setDescription("test");
		productResponse.setName("iphone");
		productResponse.setQuantity(100);
		productResponse.setPrice(BigDecimal.valueOf(2000));
		productResponse.setSkuCode("iphone");
		Product product = new Product();
		product.setId(1L);
		product.setName("iphone 13");
		product.setPrice(new BigDecimal(1200));
		product.setDescription("iphone new 13");

		when(productRepository.save(any(Product.class))).thenReturn(product);


		ProductResponse createdProduct = productServiceImpl.save(productResponse);

		assertNotNull(createdProduct);
		assertEquals("samsung", createdProduct.getName());
		assertEquals("new Samsung", createdProduct.getDescription());
		assertEquals(BigDecimal.valueOf(1300), createdProduct.getPrice());


		verify(productRepository, times(1)).save(product);
	}

}