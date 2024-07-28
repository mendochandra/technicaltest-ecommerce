package com.techtest.inventory_service.service.inventoryGetServiceImpl;

import com.techtest.inventory_service.dto.InventoryRequest;
import com.techtest.inventory_service.dto.InventoryResponse;
import com.techtest.inventory_service.model.Inventory;
import com.techtest.inventory_service.repository.InventoryRepository;
import com.techtest.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryGetServiceImpl inventoryGetService;

    @Override
    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateQuantityById(Long id, Integer quantity) {
        Optional<Inventory> findInventory = inventoryGetService.findById(id);
        if (findInventory.isPresent()){
            findInventory.get().setQuantity(quantity);
            return inventoryRepository.save(findInventory.get());
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    @Override
    public Inventory updateQuantityBySkuCode(String skuCode, Integer quantity) {
        Optional<Inventory> findInventory = inventoryGetService.findBySkuCode(skuCode);
        if (findInventory.isPresent()){
            findInventory.get().setQuantity(quantity);
            return inventoryRepository.save(findInventory.get());
        }else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    @Override
    public List<Inventory> updateListInventory(InventoryRequest inventoryRequest) {

        List<Inventory> inventories = inventoryRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        for (int i = 0 ; i < inventories.size() ; i ++){

            Optional<Inventory> inventory = inventoryGetService.findBySkuCode(inventories.get(i).getSkuCode());
            int newQuantity= inventory.get().getQuantity() -  inventories.get(i).getQuantity();
            if (newQuantity < 0) {
                throw new IllegalArgumentException("No stock, please try again later");
            }else {
                inventory.get().setQuantity(newQuantity);
            }
            inventoryRepository.save(inventory.get());

        }

        return inventories;
    }

    private Inventory mapToDto(InventoryResponse inventoryResponse) {
        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryResponse.getQuantity());
        inventory.setSkuCode(inventoryResponse.getSkuCode());
        return inventory;
    }
}
