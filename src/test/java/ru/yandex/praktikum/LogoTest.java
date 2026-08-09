package ru.yandex.praktikum;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.pageObjects.HomePage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LogoTest {

    @Rule
    public DriverFactory factory = new DriverFactory();

    @Test
    public void testTransitionOnSamokatLogo() {
        WebDriver driver = factory.getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        homePage.clickButtonByLocation(HomePage.ButtonLocation.ORDERTOP);
        homePage.clickButtonByLocation(HomePage.ButtonLocation.LOGOSAMOKAT);

        boolean isHomePage = homePage.isSloganDisplayed();
        assertTrue("Переход не на домашнюю страницу Самокат.", isHomePage);
    }

    @Test
    public void testTransitionOnYandexLogo() {
        WebDriver driver = factory.getDriver();

        HomePage homePage = new HomePage(driver);
        homePage.openHomePage();
        String homeHandle = driver.getWindowHandle();
        homePage.clickButtonByLocation(HomePage.ButtonLocation.LOGOYANDEX);

        String actualUrl = homePage.getNewTabUrl(homeHandle);
        assertTrue("Переход не на страницу Яндекса: " + actualUrl, actualUrl.contains("dzen.ru"));
    }

}
