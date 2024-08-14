package com.qeema.practicaltest.ordermanagement.domain.product.services;

import java.util.List;
import org.mockito.Mock;
import java.util.Arrays;
import java.util.Optional;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import com.qeema.practicaltest.ordermanagement.domain.product.entities.Product;
import com.qeema.practicaltest.ordermanagement.domain.order.entities.OrderProduct;
import com.qeema.practicaltest.ordermanagement.domain.product.service.ProductService;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.ResourceAlreadyExists;
import com.qeema.practicaltest.ordermanagement.domain.exceptions.ResourceNotFoundException;
import com.qeema.practicaltest.ordermanagement.domain.product.repository.ProductRepository;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProductSuccess() throws ResourceAlreadyExists {
        Product product = new Product(1L, "Test Product", 10.0, 5);

        when(productRepository.existsByName(product.getName())).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.saveProduct(product);
        assertNotNull(savedProduct);
        assertEquals("Test Product", savedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testSaveProductThrowsResourceAlreadyExists() {
        Product product = new Product(1L, "Test Product", 10.0, 5);
        when(productRepository.existsByName(product.getName())).thenReturn(true);
        assertThrows(ResourceAlreadyExists.class, () -> productService.saveProduct(product));
        verify(productRepository, never()).save(product);
    }

    @Test
    void testUpdateProductSuccess() throws ResourceNotFoundException {
        Product existingProduct = new Product(1L, "Existing Product", 15.0, 10);
        Product updatedProduct = new Product(1L, "Updated Product", 20.0, 15);

        when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.updateProduct(existingProduct.getId(), updatedProduct);
        assertNotNull(result);
        assertEquals("Updated Product", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProductThrowsResourceNotFoundException() {
        Product product = new Product(1L, "Non-Existing Product", 20.0, 5);
        when(productRepository.findById(product.getId())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(product.getId(), product));
        verify(productRepository, never()).save(product);
    }

    @Test
    void testGetProductByIdSuccess() throws ResourceNotFoundException {
        Product product = new Product(1L, "Test Product", 10.0, 5);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(product.getId());
        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void testGetProductByIdThrowsResourceNotFoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.0, 5),
                new Product(2L, "Product 2", 20.0, 10)
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> allProducts = productService.getAllProducts();

        assertEquals(2, allProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void testGetProducts() {
        OrderProduct orderProduct1 = mock(OrderProduct.class);
        OrderProduct orderProduct2 = mock(OrderProduct.class);
        when(orderProduct1.getProductName()).thenReturn("Product 1");
        when(orderProduct2.getProductName()).thenReturn("Product 2");

        List<Product> products = Arrays.asList(
                new Product(1L, "Product 1", 10.0, 5),
                new Product(2L, "Product 2", 20.0, 10)
        );

        when(productRepository.findByNameIn(anyList())).thenReturn(products);

        List<Product> result = productService.getProducts(Arrays.asList(orderProduct1, orderProduct2));

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByNameIn(anyList());
    }

    @Test
    void testReserveProducts() {
        OrderProduct orderProduct1 = mock(OrderProduct.class);
        OrderProduct orderProduct2 = mock(OrderProduct.class);

        Product product1 = new Product(1L, "Product 1", 10.0, 5);
        Product product2 = new Product(2L, "Product 2", 20.0, 10);

        when(orderProduct1.getProduct()).thenReturn(product1);
        when(orderProduct1.getQuantity()).thenReturn(2);

        when(orderProduct2.getProduct()).thenReturn(product2);
        when(orderProduct2.getQuantity()).thenReturn(3);

        productService.reserveProducts(Arrays.asList(orderProduct1, orderProduct2));

        assertEquals(3, product1.getQuantity());
        assertEquals(7, product2.getQuantity());
        verify(productRepository, times(1)).saveAll(anyList());
    }
}
