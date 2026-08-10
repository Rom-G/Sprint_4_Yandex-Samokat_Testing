package ru.yandex.praktikum.pageObjects;

import org.openqa.selenium.By;

public final class OrderWhoPageLocators {

    private OrderWhoPageLocators() {}

    public final static By NAME_FIELD = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Имя']");
    public final static By SURNAME_FIELD = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Фамилия']");
    public final static By ADDRESS_FIELD = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Адрес: куда привезти заказ']");
    public final static By METRO_FIELD = By.cssSelector("div.Order_Form__17u6u .select-search__input");
    public final static By METRO_DROPDOWN_CONTAINER = By.className("select-search__select");
    public final static By PHONE_FIELD = By.cssSelector("div.Order_Form__17u6u input[placeholder='* Телефон: на него позвонит курьер']");
    public final static By NEXT_BTN_LOCATOR = By.cssSelector("button.Button_Middle__1CSJM");
    public final static By VISIBLE_ERROR = By.cssSelector(".Input_ErrorMessage__3HvIb.Input_Visible___syz6");
    public final static By HEADER_DISCLAIMER = By.className("Header_Disclaimer__3VEni");

}
