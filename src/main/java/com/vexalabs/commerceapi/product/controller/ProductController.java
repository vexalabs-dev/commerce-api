package com.vexalabs.commerceapi.product.controller;

import com.vexalabs.commerceapi.product.model.Product;
import com.vexalabs.commerceapi.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(("/api/products"))
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable UUID id) {

        Product product = service.findById(id);

        return ResponseEntity.ok(product);

    }

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> all = service.findAll();

        return ResponseEntity.ok(all);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = service.createProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}
