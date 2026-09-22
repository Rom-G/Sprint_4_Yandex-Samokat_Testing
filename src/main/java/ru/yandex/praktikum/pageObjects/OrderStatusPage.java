package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderStatusPage {

    private final WebDriverWait wait;
    private final By notFoundImage = By.cssSelector("img[alt='Not found']");

    public OrderStatusPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, 10);
    }

    public boolean isNotFoudImageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(notFoundImage)).isDisplayed();
    }

}
