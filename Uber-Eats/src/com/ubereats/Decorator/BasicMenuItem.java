package com.ubereats.Decorator;

/*
Clase para un producto del menú sin decoradores.
Es el objeto al que luego se le pueden añadir extras,
descuentos, cambios de tamaño o eliminación de ingredientes.
*/

public class BasicMenuItem implements MenuItem{
    private String name;
    private double price;
    private String description;

    // Constructor básico

    public BasicMenuItem(String name, double price, String description){
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // Getters y setters del nombre, descripción y precio del producto

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}