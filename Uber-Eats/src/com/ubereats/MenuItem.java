package com.ubereats;
import java.util.HashMap;
import java.util.Map;

public class MenuItem {
    String name;
    Double price;
    String description;
    Map<String, Double> extras;

    public MenuItem(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.extras = new HashMap<>();
    }

    public MenuItem(String name, Double price) {
        this(name, price, null);
    }
}