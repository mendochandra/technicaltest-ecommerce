package com.techtest.inventory_service;

import com.techtest.inventory_service.model.Inventory;
import com.techtest.inventory_service.repository.InventoryRepository;
import com.techtest.inventory_service.service.InventoryService;
import com.techtest.inventory_service.service.inventoryGetServiceImpl.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class InventoryServiceImplApplicationTests {

	@InjectMocks
	private InventoryServiceImpl inventoryService;

	@Mock
	private InventoryRepository inventoryRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}


	@Test
	void updateQuantity() {
		Inventory inventory = inventoryRepository.getById(404L);
		inventory.setQuantity(150);


		// Mock the save method of the repository
		when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

		// Call the save method on the service
		Inventory createdProduct = inventoryService.updateQuantityById(inventory.getId(),inventory.getQuantity());

		// Assert that the created product is not null and has the expected properties
		assertNotNull(createdProduct);

		// Verify that the save method was called exactly once
		verify(inventoryRepository, times(1)).save(inventory);
	}

}
