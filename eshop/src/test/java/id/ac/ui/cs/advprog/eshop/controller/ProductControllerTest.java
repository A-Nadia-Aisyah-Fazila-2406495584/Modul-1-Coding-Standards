package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.controller.ProductController;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();

        product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");

        product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");
    }

    @Test
    void testShowCreateProductPage() {
        try {
            mockMvc.perform(get("/product/create"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("CreateProduct"))
                    .andExpect(model().attributeExists("product"));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testCreateProductPost() {
        try {
            when(productService.create(any(Product.class))).thenReturn(product1);

            mockMvc.perform(post("/product/create")
                            .param("productName", "New Product")
                            .param("productQuantity", "10"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("list"));

            verify(productService, times(1)).create(any(Product.class));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testProductListPage() {
        try {
            when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

            mockMvc.perform(get("/product/list"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("ProductList"))
                    .andExpect(model().attributeExists("products"));

            verify(productService, times(1)).findAll();
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testEditProductPage() {
        try {
            when(productService.findProductById("1")).thenReturn(product1);

            mockMvc.perform(get("/product/edit/1"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("EditProduct"))
                    .andExpect(model().attributeExists("product"));

            verify(productService, times(1)).findProductById("1");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testEditProductPost() {
        try {
            when(productService.edit(any(Product.class))).thenReturn(product1);

            mockMvc.perform(post("/product/edit")
                            .param("productId", "1")
                            .param("productName", "Updated Product")
                            .param("productQuantity", "5"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("list"));

            verify(productService, times(1)).edit(any(Product.class));
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testDeleteProduct() {
        try {
            when(productService.delete("1")).thenReturn(product1);

            mockMvc.perform(get("/product/delete/1"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/product/list"));

            verify(productService, times(1)).delete("1");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}