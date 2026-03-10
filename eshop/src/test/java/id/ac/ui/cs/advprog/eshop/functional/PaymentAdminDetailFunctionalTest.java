package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentAdminDetailFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    private String baseUrl;
    private String paymentId;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        orderService.createOrder(order);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BCA");
        paymentData.put("referenceCode", "REF123456");
        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);
        paymentId = payment.getId();
    }

    @Test
    void paymentAdminDetailPage_title_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + paymentId);
        assertEquals("Payment Admin Detail", driver.getTitle());
    }

    @Test
    void paymentAdminDetailPage_header_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + paymentId);
        assertEquals("Payment Detail", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    void paymentAdminDetailPage_hasBackToListLink(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + paymentId);
        assertEquals("Back to List", driver.findElement(By.linkText("Back to List")).getText());
    }

    @Test
    void paymentAdminDetailPage_backToList_navigatesToList(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/" + paymentId);
        driver.findElement(By.linkText("Back to List")).click();
        assertTrue(driver.getCurrentUrl().contains("/payment/admin/list"));
    }
}