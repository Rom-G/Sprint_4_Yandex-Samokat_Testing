package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OrderFinalPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By yesBtnLocator = By.xpath("//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Да']");
    private final By successMessage = By.cssSelector(".Order_ModalHeader__3FDaJ");

    public OrderFinalPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void clickYesButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(yesBtnLocator));
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(yesBtnLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", yesButton);
        yesButton.click();
    }

    public String getTitleOfMessage() {
        WebElement message = wait.until(ExpectedConditions.presenceOfElementLocated(successMessage));
        return message.getText().trim();
    }

}
