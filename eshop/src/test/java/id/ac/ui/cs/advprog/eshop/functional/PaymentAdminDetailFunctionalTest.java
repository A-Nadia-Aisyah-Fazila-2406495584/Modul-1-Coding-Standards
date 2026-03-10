package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

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

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void paymentAdminDetailPage_title_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/test-payment-id");
        assertEquals("Payment Admin Detail", driver.getTitle());
    }

    @Test
    void paymentAdminDetailPage_header_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/test-payment-id");
        assertEquals("Payment Detail", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    void paymentAdminDetailPage_hasBackToListLink(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/test-payment-id");
        String linkText = driver.findElement(By.linkText("Back to List")).getText();
        assertEquals("Back to List", linkText);
    }

    @Test
    void paymentAdminDetailPage_backToList_navigatesToList(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/admin/detail/test-payment-id");
        driver.findElement(By.linkText("Back to List")).click();
        assertTrue(driver.getCurrentUrl().contains("/payment/admin/list"));
    }
}