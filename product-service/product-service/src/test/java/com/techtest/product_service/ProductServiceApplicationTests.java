package com.techtest.product_service;

import com.techtest.product_service.controller.ProductController;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.github.dockerjava.core.MediaType;


import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

}
