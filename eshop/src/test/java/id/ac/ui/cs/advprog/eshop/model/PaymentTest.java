package id.ac.ui.cs.advprog.eshop.model;

//import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
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
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidLength() {
        paymentData.put("voucherCode", "ESHOP123ABC");
        Payment payment = new Payment("p-002", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherInvalidPrefix() {
        paymentData.put("voucherCode", "ABCDE1234ABC5678");
        Payment payment = new Payment("p-003", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentVoucherNotEnoughNumbers() {
        paymentData.put("voucherCode", "ESHOPABCDEFGHIJK");
        Payment payment = new Payment("p-004", "VOUCHER_CODE", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    // Bank
    @Test
    void testCreatePaymentBankTransferSuccess() {
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-005", "BANK_TRANSFER", paymentData);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyBankName() {
        paymentData.put("bankName", "");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-006", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullBankName() {
        paymentData.put("bankName", null);
        paymentData.put("referenceCode", "REF123456");
        Payment payment = new Payment("p-007", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferEmptyReferenceCode() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", "");
        Payment payment = new Payment("p-008", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentBankTransferNullReferenceCode() {
        paymentData.put("bankName", "Mandiri");
        paymentData.put("referenceCode", null);
        Payment payment = new Payment("p-009", "BANK_TRANSFER", paymentData);
        assertEquals("REJECTED", payment.getStatus());
    }
}