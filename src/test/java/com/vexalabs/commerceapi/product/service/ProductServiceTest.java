package com.vexalabs.commerceapi.product.service;

import com.vexalabs.commerceapi.product.exception.exceptions.InvalidProductDataException;
import com.vexalabs.commerceapi.product.model.Product;
import com.vexalabs.commerceapi.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repositoryMock;

    @Test
    void shouldThrowInvalidDataExceptionWhenProductNameIsNull() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                null,
                null,
                BigDecimal.valueOf(100.00)
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductNameIsEmpty() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "",
                null,
                BigDecimal.valueOf(100.00)
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductNameIsBlank() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                " ",
                null,
                BigDecimal.valueOf(100.00)
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductPriceIsZero() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "Product",
                null,
                BigDecimal.ZERO
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductPriceIsNegative() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "Product",
                null,
                BigDecimal.valueOf(-100.00)
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductPriceHasMoreThanTwoDecimalPlaces() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "Product",
                null,
                BigDecimal.valueOf(100.001)
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowInvalidDataExceptionWhenProductPriceIsNull() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "Product",
                null,
                null
        );
        assertThrows(InvalidProductDataException.class, () -> service.createProduct(product));
        verify(repositoryMock, never()).save(any(Product.class));
    }

    @Test
    void shouldSaveAEntityAtRepository() {
        ProductService service = new ProductService(repositoryMock);
        Product product = new Product(
                "Product",
                null,
                BigDecimal.valueOf(100.00)
        );
        Product result = service.createProduct(product);
        verify(repositoryMock).save(any(Product.class));

    }
}
