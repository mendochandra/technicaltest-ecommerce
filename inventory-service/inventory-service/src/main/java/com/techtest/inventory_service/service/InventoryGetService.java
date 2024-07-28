package com.techtest.inventory_service.service;

import com.techtest.inventory_service.dto.InventoryResponse;
import com.techtest.inventory_service.model.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryGetService {

    List<InventoryResponse> isInStock(List<String> skuCode);

    Optional<Inventory> findById(Long id);

    Optional<Inventory> findBySkuCode(String skuCode);

    Integer checkAvailabilityById(Long id);
    Integer checkAvailabilityBySkuCode(String skuCode);

}
