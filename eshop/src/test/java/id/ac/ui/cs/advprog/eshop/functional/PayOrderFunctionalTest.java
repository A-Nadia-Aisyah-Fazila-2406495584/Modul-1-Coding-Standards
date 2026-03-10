package id.ac.ui.cs.advprog.eshop.functional;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PayOrderFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    @Autowired
    private OrderService orderService;

    private String baseUrl;
    private String orderId;

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
        orderId = order.getId();
    }

    @Test
    void payOrderPage_title_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/pay/" + orderId);
        assertEquals("Pay Order", driver.getTitle());
    }

    @Test
    void payOrderPage_header_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/order/pay/" + orderId);
        assertEquals("Pay Order", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    void payOrderPage_hasBankTransferForm(ChromeDriver driver) {
        driver.get(baseUrl + "/order/pay/" + orderId);
        assertEquals("Bank Transfer", driver.findElement(By.tagName("h2")).getText());
    }
}