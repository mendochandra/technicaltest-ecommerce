package com.techtest.inventory_service.controller;

import com.techtest.inventory_service.dto.InventoryRequest;
import com.techtest.inventory_service.dto.InventoryResponse;
import com.techtest.inventory_service.model.Inventory;
import com.techtest.inventory_service.service.inventoryGetServiceImpl.InventoryGetServiceImpl;
import com.techtest.inventory_service.service.inventoryGetServiceImpl.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryServiceImpl inventoryServiceImpl;
    private final InventoryGetServiceImpl inventoryGetServiceImpl;

    @GetMapping
    public List<InventoryResponse> isInStock(@RequestParam List<String>  skuCode){
        return inventoryGetServiceImpl.isInStock(skuCode);
    }

    @PostMapping("/save")
    public Inventory saveInventory(@RequestBody Inventory inventory){
        return inventoryServiceImpl.save(inventory);
    }

    @PutMapping("/restock/{id}")
    public Inventory updateQuantityInventoryById(@PathVariable("id") Long id,@RequestBody Integer quanity){
        return inventoryServiceImpl.updateQuantityById(id,quanity);
    }

    @PutMapping("/restock/{skucode}")
    public Inventory updateQuantityInventoryBySkuCode(@PathVariable("id") String skucode, @RequestBody Integer quanity){
        return inventoryServiceImpl.updateQuantityBySkuCode(skucode,quanity);
    }

    @GetMapping("/{id}")
    public Integer checkAvailabilityById(@PathVariable("id") Long id){
        return inventoryGetServiceImpl.checkAvailabilityById(id);
    }

    @GetMapping("/{skucode}")
    public Integer checkAvailabilityBySkuCode(@PathVariable("skucode") String skucode){
        return inventoryGetServiceImpl.checkAvailabilityBySkuCode(skucode);
    }

    @PutMapping("/update/stock")
    public List<Inventory> updateListInventoryQuantity(@RequestBody InventoryRequest inventoryRequest){
        return inventoryServiceImpl.updateListInventory(inventoryRequest);
    }

}
