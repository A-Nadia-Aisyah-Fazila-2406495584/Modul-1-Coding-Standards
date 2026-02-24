package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");

        product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2).iterator());

        List<Product> result = productService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(product1));
        assertTrue(result.contains(product2));

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        when(productRepository.findProductById("1")).thenReturn(product1);

        Product result = productService.findProductById("1");

        assertEquals("Product 1", result.getProductName());
        verify(productRepository, times(1)).findProductById("1");
    }

    @Test
    void testCreate() {
        Product newProduct = new Product();
        newProduct.setProductName("New Product");

        doAnswer(invocation -> {
            Product p = invocation.getArgument(0);
            assertNotNull(p.getProductId());
            return null;
        }).when(productRepository).create(any(Product.class));

        Product result = productService.create(newProduct);

        assertEquals("New Product", result.getProductName());
        assertNotNull(result.getProductId());
        verify(productRepository, times(1)).create(any(Product.class));
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product1)).thenReturn(product1);

        Product result = productService.edit(product1);

        assertEquals(product1, result);
        verify(productRepository, times(1)).edit(product1);
    }

    @Test
    void testDelete() {
        when(productRepository.delete("1")).thenReturn(product1);

        Product result = productService.delete("1");

        assertEquals(product1, result);
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void testFindAllEmpty() {
        when(productRepository.findAll()).thenReturn(Collections.emptyIterator());

        List<Product> result = productService.findAll();

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }
}