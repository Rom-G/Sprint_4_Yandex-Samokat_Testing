package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cookieConfirmButton = By.id("rcc-confirm-button");
    private final By bottomOrderButton =
            By.cssSelector(".Home_FinishButton__1_cWm .Button_Button__ra12g");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openHomePage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        waitForLoadBottomOrderButton();
        tryClickCookieConfirm();
    }

    private void waitForLoadBottomOrderButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bottomOrderButton));
    }

    private void tryClickCookieConfirm() {
        try {
            WebElement button = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(cookieConfirmButton));
            button.click();
        } catch (TimeoutException e) {
            return;
        }
    }

}
