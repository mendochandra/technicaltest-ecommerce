package com.techtest.inventory_service.service;

import com.techtest.inventory_service.dto.InventoryRequest;
import com.techtest.inventory_service.dto.InventoryResponse;
import com.techtest.inventory_service.model.Inventory;

import java.math.BigDecimal;
import java.util.List;

public interface InventoryService {

    Inventory save(Inventory inventory);

    Inventory updateQuantityById(Long id, Integer quantity);

    Inventory updateQuantityBySkuCode(String skuCode, Integer quantity);

    List<Inventory> updateListInventory(InventoryRequest inventoryRequest);
}
