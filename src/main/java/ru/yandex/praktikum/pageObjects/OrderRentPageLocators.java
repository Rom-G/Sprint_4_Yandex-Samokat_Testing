package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;

public final class OrderRentPageLocators {

    private OrderRentPageLocators() {}

    public static final By DATE_FIELD = By.cssSelector(".react-datepicker__input-container input");
    public static final By RENT_DURATION_DROPDOWN = By.cssSelector(".Dropdown-control");
    public static final By COLOR_CHECKBOXES = By.cssSelector("label.Checkbox_Label__3wxSf input[type='checkbox']");
    public static final By COMMENT_FIELD = By.cssSelector("input[placeholder='Комментарий для курьера']");
    public static final By ORDER_BTN_LOCATOR = By.cssSelector("button.Button_Middle__1CSJM:not(.Button_Inverted__3IF-i)");

}
