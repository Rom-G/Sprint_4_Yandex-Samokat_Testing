package ru.yandex.praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pageObjects.OrderWhoPage;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderWhoErrorsTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    private final OrderWhoPage.FieldLocation fieldLocation;
    private final String expectedErrorMessage;

    public OrderWhoErrorsTest(OrderWhoPage.FieldLocation fieldLocation, String expectedErrorMessage) {
        this.fieldLocation = fieldLocation;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getFieldAndMessage() {
        return new Object[][] {
                {OrderWhoPage.FieldLocation.NAME, "Введите корректное имя"},
                {OrderWhoPage.FieldLocation.SURNAME, "Введите корректную фамилию"},
                {OrderWhoPage.FieldLocation.ADDRESS, "Введите корректный адрес"},
                {OrderWhoPage.FieldLocation.PHONE, "Введите корректный номер"},
        };
    }

    @Test
    public void checkNameFieldError() {
        WebDriver driver = factory.getDriver();
        OrderWhoPage orderWhoPage = new OrderWhoPage(driver);
        orderWhoPage.openOrderWhoPage();
        String actualMessage = orderWhoPage.findFieldErrorText(fieldLocation, "A");
        assertEquals(
                "Получено сообщение: " + actualMessage + ". Ожидалось: " + expectedErrorMessage,
                expectedErrorMessage,
                actualMessage);
    }

}
