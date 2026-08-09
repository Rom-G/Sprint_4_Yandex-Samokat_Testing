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
    private final By dateField = By.cssSelector(".react-datepicker__input-container input");
    private final By rentDurationDropdown = By.cssSelector(".Dropdown-control");
    private final By colorCheckboxes = By.cssSelector("label.Checkbox_Label__3wxSf input[type='checkbox']");
    private final By commentField = By.cssSelector("input[placeholder='Комментарий для курьера']");
    private final By orderBtnLocator = By.cssSelector("button.Button_Middle__1CSJM:not(.Button_Inverted__3IF-i)");

    public OrderRentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void fillRentDetails(RentData data) {
        fillInputField(dateField, data.getDate());
        selectDuration(data.getDuration());
        selectColor(data.getColor());
        fillInputField(commentField, data.getComment());
    }

    private void fillInputField(By locator, String text) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        inputField.clear();
        inputField.sendKeys(text);
        driver.findElement(By.tagName("body")).click();
    }

    private void selectDuration(String durationText) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentDurationDropdown));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", dropdown);
        dropdown.click();

        By durationLocator = By.xpath("//div[contains(@class, 'Dropdown-option') and contains(normalize-space(), '" + durationText + "')]");
        WebElement duration = wait.until(ExpectedConditions.elementToBeClickable(durationLocator));
        duration.click();
    }

    private void selectColor(String color) {
        List<WebElement> checkboxes = driver.findElements(colorCheckboxes);

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
        WebElement orderButton = wait.until(ExpectedConditions.elementToBeClickable(orderBtnLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", orderButton);
        orderButton.click();
    }

}
