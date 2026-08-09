package ru.yandex.praktikum.dto;

public class OrderData {

    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;

    public OrderData(String name, String surname, String address, String metro, String phone) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getAddress() {
        return address;
    }
    public String getMetro() {
        return metro;
    }
    public String getPhone() {
        return phone;
    }
}
