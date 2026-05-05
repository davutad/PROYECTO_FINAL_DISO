package com.ubereats.Decorator;

/*
Producto del menú
 Define las operaciones que debe tener cualquier elemento del menú
*/

public interface MenuItem {
    String getName();
    double getPrice();
    String getDescription();
}