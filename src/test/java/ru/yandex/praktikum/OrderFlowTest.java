package ru.yandex.praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.dto.OrderData;
import ru.yandex.praktikum.dto.RentData;
import ru.yandex.praktikum.pageObjects.HomePage;
import ru.yandex.praktikum.pageObjects.OrderFinalPage;
import ru.yandex.praktikum.pageObjects.OrderRentPage;
import ru.yandex.praktikum.pageObjects.OrderWhoPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFlowTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    private final HomePage.ButtonLocation buttonLocation;
    private final OrderData whoData;
    private final RentData rentData;

    public OrderFlowTest(HomePage.ButtonLocation buttonLocation, OrderData whoData, RentData rentData) {
        this.buttonLocation = buttonLocation;
        this.whoData = whoData;
        this.rentData = rentData;
    }

    @Parameterized.Parameters
    public static Object[][] data() {
        OrderData whoData1 = new OrderData(
                "Иван", "Иванов",
                "ул. Ленина, д. 1",
                "Охотный Ряд",
                "+79990000001"
        );
        RentData rentData1 = new RentData(
                "25.10.2026",
                "трое суток",
                "чёрный жемчуг",
                "Привезти до 12:00"
        );
        OrderData whoData2 = new OrderData(
                "Пётр", "Петров",
                "пр. Мира, д. 7",
                "Проспект Мира",
                "+79990000002"
        );
        RentData rentData2 = new RentData(
                "26.10.2026",
                "сутки",
                "серая безысходность",
                "Оставить у консьержа"
        );

        return new Object[][] {
                // Прогон 1: Верхняя кнопка + Данные Ивана
                {HomePage.ButtonLocation.TOP, whoData1, rentData1},

                // Прогон 2: Нижняя кнопка + Данные Петра
                {HomePage.ButtonLocation.BOTTOM, whoData2, rentData2},
        };
    }

    @Test
    public void testOrderFlow() {
        WebDriver driver = factory.getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.clickOrderButton(buttonLocation);

        OrderWhoPage orderWhoPage = new OrderWhoPage(driver);
        orderWhoPage.fillForm(whoData);
        orderWhoPage.clickNextButton();

        OrderRentPage orderRentPage = new OrderRentPage(driver);
        orderRentPage.fillRentDetails(rentData);
        orderRentPage.clickOrderButton();

        OrderFinalPage orderFinalPage = new OrderFinalPage(driver);
        orderFinalPage.clickYesButton();
        String actualMessage = orderFinalPage.getTitleOfMessage();

        assertTrue(
                "Неверное сообщение в попапе. Ожидалось 'Заказ оформлен'. Текст: " + actualMessage,
                actualMessage.toLowerCase().contains("заказ оформлен")
        );
    }

}
