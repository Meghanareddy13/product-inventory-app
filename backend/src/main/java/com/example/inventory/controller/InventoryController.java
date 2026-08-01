package com.example.inventory.controller;

import com.example.inventory.model.Product;
import com.example.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        return ResponseEntity.status(201).body(inventoryService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Integer> getStock(@PathVariable Long id) {
        return ResponseEntity.ok(inventoryService.getAvailableStock(id));
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<String> reserveStock(@PathVariable Long id, @RequestParam int quantity) {
        int available = inventoryService.getAvailableStock(id);
        if (quantity > available) {
            return ResponseEntity.badRequest().body("Not enough stock");
        }
        inventoryService.reduceStock(id, quantity);
        return ResponseEntity.ok("Reserved successfully");
    }
}
