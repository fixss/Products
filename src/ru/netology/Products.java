package ru.netology;

public class Products {
    private double price;
    private String name;

    public Products(double price, String name) {
        this.price = price;
        this.name = name;
    }

    public Products() {
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}