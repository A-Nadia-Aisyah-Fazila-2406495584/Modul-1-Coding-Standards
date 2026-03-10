package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private PaymentService paymentService;

    private Order order;
    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p-001");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");
        payment = new Payment("pay-001", order.getId(), "BANK_TRANSFER", paymentData);
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateOrder"));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"));
    }

    @Test
    void testOrderHistoryPost() throws Exception {
        when(orderService.findAllByAuthor("Safira Sudrajat")).thenReturn(List.of(order));

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"))
                .andExpect(model().attributeExists("orders"))
                .andExpect(model().attribute("author", "Safira Sudrajat"));
    }

    @Test
    void testOrderHistoryPost_noOrders() throws Exception {
        when(orderService.findAllByAuthor("Unknown")).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/order/history")
                        .param("author", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(view().name("OrderHistory"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        when(orderService.findById("o-001")).thenReturn(order);

        mockMvc.perform(get("/order/pay/o-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("PayOrder"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPayOrderPost_bankTransfer() throws Exception {
        when(orderService.findById("o-001")).thenReturn(order);
        when(paymentService.addPayment(any(), eq("BANK_TRANSFER"), anyMap())).thenReturn(payment);

        mockMvc.perform(post("/order/pay/o-001")
                        .param("method", "BANK_TRANSFER")
                        .param("bankName", "BCA")
                        .param("referenceCode", "REF123"))
                .andExpect(status().isOk())
                .andExpect(view().name("PayOrderSuccess"))
                .andExpect(model().attributeExists("paymentId"));
    }

    @Test
    void testPayOrderPost_voucherCode() throws Exception {
        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234NUM5678");
        Payment voucherPayment = new Payment("pay-002", order.getId(), "VOUCHER_CODE", voucherData);

        when(orderService.findById("o-001")).thenReturn(order);
        when(paymentService.addPayment(any(), eq("VOUCHER_CODE"), anyMap())).thenReturn(voucherPayment);

        mockMvc.perform(post("/order/pay/o-001")
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP1234NUM5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("PayOrderSuccess"))
                .andExpect(model().attributeExists("paymentId"));
    }
}