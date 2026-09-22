package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.dto.OrderData;

public class OrderWhoPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public OrderWhoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openOrderWhoPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");
    }

    public void fillForm(OrderData data) {
        fillInput(OrderWhoPageLocators.NAME_FIELD, data.getName());
        fillInput(OrderWhoPageLocators.SURNAME_FIELD, data.getSurname());
        fillInput(OrderWhoPageLocators.ADDRESS_FIELD, data.getAddress());
        selectMetro(data.getMetro());
        fillInput(OrderWhoPageLocators.PHONE_FIELD, data.getPhone());
    }

    private void fillInput(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }

    private void selectMetro(String metroName) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(OrderWhoPageLocators.METRO_FIELD));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", inputField);
        inputField.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(OrderWhoPageLocators.METRO_DROPDOWN_CONTAINER));

        String xpath = "//div[contains(@class, 'select-search__select')]//*[contains(text(), '" + metroName + "')]";
        By stationLocator = By.xpath(xpath);

        WebElement station = wait.until(ExpectedConditions.elementToBeClickable(stationLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", station);
        station.click();

        wait.until(d -> {
            String value = inputField.getAttribute("value");
            return value != null && !value.isEmpty();
        });
    }

    public void clickNextButton() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(OrderWhoPageLocators.NEXT_BTN_LOCATOR));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", nextButton);
        nextButton.click();
    }

    public String findFieldErrorText(FieldLocation location, String wrongText) {
        By fieldLocator = findFieldLocator(location);
        fillInput(fieldLocator, wrongText);
        driver.findElement(OrderWhoPageLocators.HEADER_DISCLAIMER).click();
        WebElement error = findVisibleErrorElement();
        return error.getText().trim();
    }

    public By findFieldLocator(FieldLocation location) {
        By locator;
        if (location == FieldLocation.NAME) {
            locator = OrderWhoPageLocators.NAME_FIELD;
        } else if (location == FieldLocation.SURNAME) {
            locator = OrderWhoPageLocators.SURNAME_FIELD;
        } else if (location == FieldLocation.ADDRESS) {
            locator = OrderWhoPageLocators.ADDRESS_FIELD;
        } else if (location == FieldLocation.PHONE) {
            locator = OrderWhoPageLocators.PHONE_FIELD;
        } else {
            throw new IllegalArgumentException("Неизвестная локация поля: " + location);
        }

        return locator;
    }

    private WebElement findVisibleErrorElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(OrderWhoPageLocators.VISIBLE_ERROR));
    }

    public enum FieldLocation {NAME, SURNAME, ADDRESS, PHONE}

}
