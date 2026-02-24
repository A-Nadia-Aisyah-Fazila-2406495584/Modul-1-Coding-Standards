package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductId("1");
        product1.setProductName("Product 1");

        product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Product 2");
    }

    @Test
    void testShowCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        when(productService.create(any(Product.class))).thenReturn(product1);

        mockMvc.perform(post("/product/create")
                        .param("productName", "New Product")
                        .param("productQuantity", "10"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).create(any(Product.class));
    }

    @Test
    void testProductListPage() throws Exception {
        when(productService.findAll()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attributeExists("products"));

        verify(productService, times(1)).findAll();
    }

    @Test
    void testEditProductPage() throws Exception {
        when(productService.findProductById("1")).thenReturn(product1);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("product"));

        verify(productService, times(1)).findProductById("1");
    }

    @Test
    void testEditProductPost() throws Exception {
        when(productService.edit(any(Product.class))).thenReturn(product1);

        mockMvc.perform(post("/product/edit")
                        .param("productId", "1")
                        .param("productName", "Updated Product")
                        .param("productQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService, times(1)).edit(any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        when(productService.delete("1")).thenReturn(product1);

        mockMvc.perform(get("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService, times(1)).delete("1");
    }
}