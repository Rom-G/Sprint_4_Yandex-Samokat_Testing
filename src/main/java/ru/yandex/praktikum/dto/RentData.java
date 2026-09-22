package ru.yandex.praktikum.dto;

public class RentData {

    private final String date;
    private final String duration;
    private final String color;
    private final String comment;

    public RentData(String date, String duration, String color, String comment) {
        this.date = date;
        this.duration = duration;
        this.color = color;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }
    public String getDuration() {
        return duration;
    }
    public String getColor() {
        return color;
    }
    public String getComment() {
        return comment;
    }
}
