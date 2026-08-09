package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.dto.OrderData;

public class OrderWhoPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By nameField = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Имя']");
    private final By surnameField = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Фамилия']");
    private final By addressField = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.cssSelector("div.Order_Form__17u6u .select-search__input");
    private final By metroDropdownContainer = By.className("select-search__select");
    private final By phoneField = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Телефон: на него позвонит курьер']");
    private final By nextBtnLocator = By.cssSelector("button.Button_Middle__1CSJM");

    public OrderWhoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openOrderWhoPage() {
        driver.get("https://qa-scooter.praktikum-services.ru/order");
    }

    public void fillForm(OrderData data) {
        fillInput(nameField, data.getName());
        fillInput(surnameField, data.getSurname());
        fillInput(addressField, data.getAddress());
        selectMetro(data.getMetro());
        fillInput(phoneField, data.getPhone());
    }

    private void fillInput(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }

    private void selectMetro(String metroName) {
        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(metroField));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", inputField);
        inputField.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(metroDropdownContainer));

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
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(nextBtnLocator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", nextButton);
        nextButton.click();
    }

    public String findFieldErrorText(FieldLocation location, String wrongText) {
        By fieldLocator = findFieldLocator(location);
        fillInput(fieldLocator, wrongText);
        driver.findElement(By.className("Header_Disclaimer__3VEni")).click();
        WebElement error = findVisibleErrorElement();
        return error.getText().trim();
    }

    public By findFieldLocator(FieldLocation location) {
        By locator;
        if (location == FieldLocation.NAME) {
            locator = nameField;
        } else if (location == FieldLocation.SURNAME) {
            locator = surnameField;
        } else if (location == FieldLocation.ADDRESS) {
            locator = addressField;
        } else if (location == FieldLocation.PHONE) {
            locator = phoneField;
        } else {
            throw new IllegalArgumentException("Неизвестная локация поля: " + location);
        }

        return locator;
    }

    private WebElement findVisibleErrorElement() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".Input_ErrorMessage__3HvIb.Input_Visible___syz6")));
    }

    public enum FieldLocation {NAME, SURNAME, ADDRESS, PHONE}

}
