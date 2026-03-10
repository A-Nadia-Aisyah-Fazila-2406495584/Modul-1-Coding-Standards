package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
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

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService paymentService;

    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p-001");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("o-001", products, 1708560000L, "Safira Sudrajat");

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");
        payment = new Payment("pay-001", order.getId(), "BANK_TRANSFER", paymentData);
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetail"));
    }

    @Test
    void testPaymentDetailById_found() throws Exception {
        when(paymentService.getPayment("pay-001")).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/pay-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetailResult"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentDetailById_notFound() throws Exception {
        when(paymentService.getPayment("not-exist")).thenReturn(null);

        mockMvc.perform(get("/payment/detail/not-exist"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentDetailResult"))
                .andExpect(model().attribute("payment", (Object) null));
    }

    @Test
    void testPaymentAdminList() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(List.of(payment));

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testPaymentAdminList_empty() throws Exception {
        when(paymentService.getAllPayments()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminList"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testPaymentAdminDetail_found() throws Exception {
        when(paymentService.getPayment("pay-001")).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/pay-001"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminDetail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPaymentAdminDetail_notFound() throws Exception {
        when(paymentService.getPayment("not-exist")).thenReturn(null);

        mockMvc.perform(get("/payment/admin/detail/not-exist"))
                .andExpect(status().isOk())
                .andExpect(view().name("PaymentAdminDetail"))
                .andExpect(model().attribute("payment", (Object) null));
    }

    @Test
    void testSetPaymentStatus_success() throws Exception {
        when(paymentService.getPayment("pay-001")).thenReturn(payment);
        when(paymentService.setStatus(any(), eq("SUCCESS"))).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/pay-001")
                        .param("status", "SUCCESS"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/pay-001"));
    }

    @Test
    void testSetPaymentStatus_rejected() throws Exception {
        when(paymentService.getPayment("pay-001")).thenReturn(payment);
        when(paymentService.setStatus(any(), eq("REJECTED"))).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/pay-001")
                        .param("status", "REJECTED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/pay-001"));
    }
}