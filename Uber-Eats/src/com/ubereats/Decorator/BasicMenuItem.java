package com.ubereats.Decorator;

public class BasicMenuItem implements MenuItemComponent, MenuItem {
    private String name;
    private double price;
    private String description;

    public BasicMenuItem(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void print() {
        System.out.println("===" + getName() + "===");
        System.out.println("Description: " + getDescription());
        System.out.println("Price: " + String.format("%.2f", getPrice()) + "$");
    }
}