package com.ubereats;

public enum Size{
    SMALL(0.8, "Small"),
    MEDIUM(1.0, "Medium"),
    LARGE(1.3, "Large"),
    EXTRA_LARGE(1.6, "Extra large");

    private final Double priceMultiplier;
    private final String label;

    Size(Double priceMultiplier, String label){
        this.priceMultiplier = priceMultiplier;
        this.label = label;
    }

    public Double getPriceMultiplier() {
        return this.priceMultiplier;
    }
    public String getLabel() {
        return this.label;
    }

}