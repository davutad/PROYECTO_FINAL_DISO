package com.ubereats.Decorator;

/*
Enum que representa los tamaños disponibles para un producto.
Cada tamaño tiene un multiplicador que afecta al precio final.
*/

public enum Size{
    SMALL(0.8, "Small"),
    MEDIUM(1.0, "Medium"),
    LARGE(1.3, "Large"),
    EXTRA_LARGE(1.6, "Extra large");

    private final double priceMultiplier;
    private final String label;

    Size(double priceMultiplier, String label){
        this.priceMultiplier = priceMultiplier;
        this.label = label;
    }

    public double getPriceMultiplier() {
        return this.priceMultiplier;
    }
    public String getLabel() {
        return this.label;
    }

}