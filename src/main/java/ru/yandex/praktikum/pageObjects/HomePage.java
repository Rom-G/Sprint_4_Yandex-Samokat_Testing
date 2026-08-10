package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openHomePage() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
        waitForLoadBottomOrderButton();
        tryClickCookieConfirm();
    }

    public boolean isSloganDisplayed() {
        WebElement slogan = wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageLocators.SLOGAN_LOCATOR));
        return slogan != null;
    }

    public String waitForNewTabAndReturnUrl(String homeHandle) {
        wait.until(d -> d.getWindowHandles().size() > 1);
        Set<String> handles = driver.getWindowHandles();

        String newTabHandle = null;
        for (String handle : handles) {
            if (!handle.equals(homeHandle)) {
                newTabHandle = handle;
                break;
            }
        }

        if (newTabHandle == null) {
            throw new RuntimeException("Новая вкладка не открылась");
        }

        driver.switchTo().window(newTabHandle);

        wait.until(d -> {
            String currentUrl = d.getCurrentUrl();
            return currentUrl != null
                    && !currentUrl.isEmpty()
                    && !"about:blank".equals(currentUrl);
        });

        return driver.getCurrentUrl();
    }

    public void enterOrderNumber(String orderNumber) {
        clickButtonByLocation(ButtonLocation.ORDERSTATUSBUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(HomePageLocators.ORDER_STATUS_INPUT));
        driver.findElement(HomePageLocators.ORDER_STATUS_INPUT).sendKeys(orderNumber);
        clickButtonByLocation(ButtonLocation.GOSTATUSBUTTON);
    }

    public void clickButtonByLocation(ButtonLocation location) {
        By locator;
        if (location == ButtonLocation.ORDERTOP) {
            locator = HomePageLocators.TOP_ORDER_BUTTON;
        } else if (location == ButtonLocation.ORDERBOTTOM) {
            locator = HomePageLocators.BOTTOM_ORDER_BUTTON;
        } else if (location == ButtonLocation.LOGOYANDEX) {
            locator = HomePageLocators.YANDEX_LOGO;
        } else if (location == ButtonLocation.LOGOSAMOKAT) {
            locator = HomePageLocators.SAMOKAT_LOGO;
        } else if (location == ButtonLocation.ORDERSTATUSBUTTON) {
            locator = HomePageLocators.ORDER_STATUS_BUTTON;
        } else if (location == ButtonLocation.GOSTATUSBUTTON) {
            locator = HomePageLocators.GO_STATUS_BUTTON;
        } else {
            throw new IllegalArgumentException("Неизвестная локация кнопки: " + location);
        }

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }

    private void waitForLoadBottomOrderButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageLocators.BOTTOM_ORDER_BUTTON));
    }

    private void tryClickCookieConfirm() {
        try {
            WebElement button = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(HomePageLocators.COOKIE_CONFIRM_BUTTON));
            button.click();
        } catch (TimeoutException ignored) {
        }
    }

    public enum ButtonLocation {
        ORDERTOP,
        ORDERBOTTOM,
        LOGOYANDEX,
        LOGOSAMOKAT,
        ORDERSTATUSBUTTON,
        GOSTATUSBUTTON
    }

}
