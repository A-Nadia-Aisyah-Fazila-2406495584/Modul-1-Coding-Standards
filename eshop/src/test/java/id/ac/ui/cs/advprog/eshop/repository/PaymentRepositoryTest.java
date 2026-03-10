package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123");
    }

    @Test
    void testSavePayment() {
        Payment payment = new Payment("p-001", "BANK_TRANSFER", paymentData);
        Payment result = paymentRepository.save(payment);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testFindByIdFound() {
        Payment payment = new Payment("p-001", "BANK_TRANSFER", paymentData);
        paymentRepository.save(payment);
        Payment result = paymentRepository.findById("p-001");
        assertEquals("p-001", result.getId());
    }

    @Test
    void testFindByIdNotFound() {
        Payment result = paymentRepository.findById("not-exist");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        Payment p1 = new Payment("p-001", "BANK_TRANSFER", paymentData);
        Payment p2 = new Payment("p-002", "VOUCHER_CODE",
                new HashMap<>(Map.of("voucherCode", "ESHOP1234ABC5678")));
        paymentRepository.save(p1);
        paymentRepository.save(p2);
        List<Payment> all = paymentRepository.getAllPayments();
        assertEquals(2, all.size());
    }

    @Test
    void testSaveUpdateExistingPayment() {
        Payment payment = new Payment("p-001", "BANK_TRANSFER",
                new HashMap<>(Map.of("bankName", "Mandiri", "referenceCode", "REF123")));
        paymentRepository.save(payment);

        payment.setStatus("REJECTED");
        Payment updated = paymentRepository.save(payment);

        assertEquals("REJECTED", updated.getStatus());
        assertEquals(1, paymentRepository.getAllPayments().size());
    }
}