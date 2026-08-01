package com.example.inventory.service;

import com.example.inventory.model.Product;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.exception.ProductNotFoundException;
import com.example.inventory.exception.InsufficientStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public int getAvailableStock(Long productId) {
        return inventoryRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId))
            .getStock();
    }

    @Transactional
    public void reduceStock(Long productId, int quantity) {
        Product product = inventoryRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));
        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Not enough stock for product: " + productId);
        }
        product.setStock(product.getStock() - quantity);
        inventoryRepository.save(product);
    }

    public Product save(Product product) {
        return inventoryRepository.save(product);
    }
}
