package com.techtest.inventory_service.service.inventoryGetServiceImpl;

import com.techtest.inventory_service.dto.InventoryResponse;
import com.techtest.inventory_service.model.Inventory;
import com.techtest.inventory_service.repository.InventoryRepository;
import com.techtest.inventory_service.service.InventoryGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryGetServiceImpl implements InventoryGetService {

    private final InventoryRepository inventoryRepository;

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode){

        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .build()
                ).toList();

    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public Optional<Inventory> findBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode);
    }

    @Override
    public Integer checkAvailabilityById(Long id) {
        Optional<Inventory> inventory = findById(id);
        String message = ("No Product with ")+ id +( "Id, please try again later") ;
        if (inventory.isPresent()){
            return inventory.get().getQuantity();
        }else {
            throw new IllegalArgumentException(message);
        }

    }

    @Override
    public Integer checkAvailabilityBySkuCode(String skuCode) {
        Optional<Inventory> inventory = findBySkuCode(skuCode);
        String message = ("No Product with ")+ skuCode +( "skuCode, please try again later") ;
        if (inventory.isPresent()){
            return inventory.get().getQuantity();
        }else {
            throw new IllegalArgumentException(message);
        }
    }


}
