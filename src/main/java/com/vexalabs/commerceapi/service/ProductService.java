package com.vexalabs.commerceapi.service;

import com.vexalabs.commerceapi.model.Product;
import com.vexalabs.commerceapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Product createProduct(Product product) {

        if (product.getSalePrice() == null || product.getSalePrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço não pode ser menor ou igual a zero");
        }

        if (product.getSalePrice().scale() > 2) {
            throw new IllegalArgumentException("O preço do produto precisa ter no máximo dois digitos após a virgula");
        }

        if (product.getName() == null || product.getName().isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório");
        }

        Product newProduct = new Product(product.getName(), product.getDescription(), product.getSalePrice());

        repository.save(newProduct);

        return newProduct;
    }

    public Product findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recurso não encontrado"));
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

}
