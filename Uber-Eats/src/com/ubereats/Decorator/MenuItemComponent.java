package com.ubereats.Decorator;

/*
Interfáz común del patrón 
*/

public interface MenuItemComponent {
    String getName();
    double getPrice();
    String getDescription();
    void print();
}