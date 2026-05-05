package com.ubereats.Decorator;

public interface MenuItemComponent {
    String getName();
    double getPrice();
    String getDescription();
    void print();
}