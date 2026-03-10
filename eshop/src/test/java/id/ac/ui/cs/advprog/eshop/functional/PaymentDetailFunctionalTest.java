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
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class PaymentDetailFunctionalTest {

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
    void paymentDetailPage_title_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");
        assertEquals("Payment Detail", driver.getTitle());
    }

    @Test
    void paymentDetailPage_header_isCorrect(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail");
        assertEquals("Check Payment Detail", driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    void paymentDetailResult_notFound_showsMessage(ChromeDriver driver) {
        driver.get(baseUrl + "/payment/detail/not-exist-id");
        assertEquals("Payment not found.", driver.findElement(By.tagName("p")).getText());
    }
}