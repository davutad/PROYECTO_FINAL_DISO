package com.ubereats.Decorator;

public class ExtraDecorator extends MenuItemDecorator{
    private String extraName;
    private Double extraPrice;

    public ExtraDecorator(MenuItemComponent menuItemComponent, String extraName, Double extraPrice){
        super(menuItemComponent);
        this.extraName = extraName;
        this.extraPrice = extraPrice;
    }

    @Override
    public String getName() {
        return menuItemComponent.getName() + " + " + extraName;
    }

    @Override
    public double getPrice() {
        return menuItemComponent.getPrice() + extraPrice;
    }

    @Override
    public String getDescription() {
        return menuItemComponent.getDescription() + " | Extra: " + extraName;
    }
}