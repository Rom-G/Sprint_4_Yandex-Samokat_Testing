package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By cookieConfirmButton = By.id("rcc-confirm-button");
    private final By bottomOrderButton = By.cssSelector(".Home_FinishButton__1_cWm .Button_Button__ra12g");
    private final By topOrderButton = By.cssSelector(".Header_Nav__AGCXC .Button_Button__ra12g");
    private final By samokatLogo = By.xpath("//img[@alt='Scooter']/parent::a");
    private final By yandexLogo = By.xpath("//img[@alt='Yandex']/parent::a");
    private final By sloganLocator = By.xpath("//div[contains(@class, 'Home_Header__iJKdX') and contains(normalize-space(), 'Самокат') and contains(normalize-space(), 'пару дней')]");
    private final By orderStatusButton = By.className("Header_Link__1TAG7");
    private final By orderStatusInput = By.className("Input_Input__1iN_Z");
    private final By goStatusButton = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    private final String homeUrl = "https://qa-scooter.praktikum-services.ru/";

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 10);
    }

    public void openHomePage() {
        driver.get(homeUrl);
        waitForLoadBottomOrderButton();
        tryClickCookieConfirm();
    }

    public boolean isSloganDisplayed() {
        WebElement slogan = wait.until(ExpectedConditions.visibilityOfElementLocated(sloganLocator));
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
        wait.until(ExpectedConditions.elementToBeClickable(orderStatusInput));
        driver.findElement(orderStatusInput).sendKeys(orderNumber);
        clickButtonByLocation(ButtonLocation.GOSTATUSBUTTON);
    }

    public void clickButtonByLocation(ButtonLocation location) {
        By locator;
        if (location == ButtonLocation.ORDERTOP) {
            locator = topOrderButton;
        } else if (location == ButtonLocation.ORDERBOTTOM) {
            locator = bottomOrderButton;
        } else if (location == ButtonLocation.LOGOYANDEX) {
            locator = yandexLogo;
        } else if (location == ButtonLocation.LOGOSAMOKAT) {
            locator = samokatLogo;
        } else if (location == ButtonLocation.ORDERSTATUSBUTTON) {
            locator = orderStatusButton;
        } else if (location == ButtonLocation.GOSTATUSBUTTON) {
            locator = goStatusButton;
        } else {
            throw new IllegalArgumentException("Неизвестная локация кнопки: " + location);
        }

        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", button);
        button.click();
    }

    private void waitForLoadBottomOrderButton() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(bottomOrderButton));
    }

    private void tryClickCookieConfirm() {
        try {
            WebElement button = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.elementToBeClickable(cookieConfirmButton));
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
