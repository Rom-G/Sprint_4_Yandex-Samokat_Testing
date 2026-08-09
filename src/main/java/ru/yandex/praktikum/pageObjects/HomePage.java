package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cookieConfirmButton = By.id("rcc-confirm-button");
    private final By bottomOrderButton = By.cssSelector(".Home_FinishButton__1_cWm .Button_Button__ra12g");
    private final By topOrderButton = By.cssSelector(".Header_Nav__AGCXC .Button_Button__ra12g");

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

    public void clickOrderButton(ButtonLocation location) {
        By locator;
        if (location == ButtonLocation.TOP) {
            locator = topOrderButton;
        } else if (location == ButtonLocation.BOTTOM) {
            locator = bottomOrderButton;
        } else {
            throw new IllegalArgumentException("Неизвестная локация кнопки: " + location);
        }

        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", orderButton);
        orderButton.click();
    }

    public enum ButtonLocation { TOP, BOTTOM }

}
