package id.ac.ui.cs.advprog.eshop.model;

//import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
    }

    // Voucher
    @Test
    void testCreatePaymentVoucherSuccess() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("p-001", "VOUCHER_CODE", paymentData);
        assertEquals("p-001", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        paymentData.put("voucherCode", "ESHOP123ABC");
        Payment payment = new Payment("p-002", "VOUCHER_CODE", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidPrefix() {
        paymentData.put("voucherCode", "ABCDE1234ABC5678");
        Payment payment = new Payment("p-003", "VOUCHER_CODE", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherNotEnoughNumbers() {
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("p-004", "VOUCHER_CODE", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    // Bank
    @Test
    void testCreatePaymentBankTransferSuccess() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-005", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyBankName() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-006", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullBankName() {
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-007", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyReferenceCode() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("p-008", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullReferenceCode() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("p-009", "BANK_TRANSFER", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Payment payment = new Payment("p-010", "INVALID_METHOD", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherNullCode() {
        paymentData.put("voucherCode", null);
        Payment payment = new Payment("p-011", "VOUCHER_CODE", paymentData);
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testGetPaymentData() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-012", "BANK_TRANSFER", paymentData);
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetStatus() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-013", "BANK_TRANSFER", paymentData);
        payment.setStatus(PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
    }

    @Test
    void testPaymentStatusContainsValidStatus() {
        assertTrue(PaymentStatus.contains("SUCCESS"));
        assertTrue(PaymentStatus.contains("REJECTED"));
        assertTrue(PaymentStatus.contains("WAITING_PAYMENT"));
    }

    @Test
    void testPaymentStatusContainsInvalidStatus() {
        assertFalse(PaymentStatus.contains("INVALID"));
    }

    @Test
    void testPaymentStatusGetValue() {
        assertEquals("SUCCESS", PaymentStatus.SUCCESS.getValue());
        assertEquals("REJECTED", PaymentStatus.REJECTED.getValue());
        assertEquals("WAITING_PAYMENT", PaymentStatus.WAITING_PAYMENT.getValue());
    }

    @Test
    void testCreatePaymentWithOrderId() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");
        Payment payment = new Payment("p-001", "order-001", "BANK_TRANSFER", paymentData);
        assertEquals("p-001", payment.getId());
        assertEquals("order-001", payment.getOrderId());
    }
}