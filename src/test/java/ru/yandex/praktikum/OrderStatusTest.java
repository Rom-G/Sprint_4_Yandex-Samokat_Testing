package ru.yandex.praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pageObjects.HomePage;
import ru.yandex.praktikum.pageObjects.OrderStatusPage;

import static org.junit.Assert.assertTrue;

public class OrderStatusTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    @Test
    public void testOrderNotFoundImage() {
        WebDriver driver = factory.getDriver();
        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.enterOrderNumber("12345");

        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);

        assertTrue("Изображение не отображается", orderStatusPage.isNotFoudImageDisplayed());
    }

}
