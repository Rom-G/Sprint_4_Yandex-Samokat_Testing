package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;

public final class HomePageLocators {

    private HomePageLocators() {}

    public static final By COOKIE_CONFIRM_BUTTON = By.id("rcc-confirm-button");
    public static final By BOTTOM_ORDER_BUTTON = By.cssSelector(".Home_FinishButton__1_cWm .Button_Button__ra12g");
    public static final By TOP_ORDER_BUTTON = By.cssSelector(".Header_Nav__AGCXC .Button_Button__ra12g");
    public static final By SAMOKAT_LOGO = By.xpath("//img[@alt='Scooter']/parent::a");
    public static final By YANDEX_LOGO = By.xpath("//img[@alt='Yandex']/parent::a");
    public static final By SLOGAN_LOCATOR = By.xpath(
            "//div[contains(@class, 'Home_Header__iJKdX') " +
                    "and contains(normalize-space(), 'Самокат') " +
                    "and contains(normalize-space(), 'пару дней')]"
    );
    public static final By ORDER_STATUS_BUTTON = By.className("Header_Link__1TAG7");
    public static final By ORDER_STATUS_INPUT = By.className("Input_Input__1iN_Z");
    public static final By GO_STATUS_BUTTON = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");

}
