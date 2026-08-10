package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.dto.RentData;

import java.util.List;

public class OrderRentPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderRentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void fillRentDetails(RentData data) {
        fillInputField(OrderRentPageLocators.DATE_FIELD, data.getDate());
        selectDuration(data.getDuration());
        selectColor(data.getColor());
        fillInputField(OrderRentPageLocators.COMMENT_FIELD, data.getComment());
    }

    private void fillInputField(By locator, String text) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(text);
        driver.findElement(By.tagName("body")).click();
    }

    private void selectDuration(String durationText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(OrderRentPageLocators.RENT_DURATION_DROPDOWN));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", dropdown);
        dropdown.click();

        By durationLocator = By.xpath("//div[contains(@class, 'Dropdown-option') and contains(normalize-space(), '" + durationText + "')]");
        WebElement duration = wait.until(ExpectedConditions.elementToBeClickable(durationLocator));
        duration.click();
    }

    private void selectColor(String color) {
        List<WebElement> checkboxes = driver.findElements(OrderRentPageLocators.COLOR_CHECKBOXES);

        for (WebElement cb : checkboxes) {
            //Поднимаемся к родительскому label, чтобы получить текст
            WebElement label = cb.findElement(By.xpath("./ancestor::label"));
            String labelText = label.getText().trim();

            if (labelText.equals(color)) {
                ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", cb);
                cb.click();
                return;
            }
        }
        throw new RuntimeException("Цвет '" + color + "' не найден среди чекбоксов.");
    }

    public void clickOrderButton() {
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(OrderRentPageLocators.ORDER_BTN_LOCATOR));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", orderButton);
        orderButton.click();
    }

}
