package com.ubereats.Decorator;

/*
Decorador que aplica un descuento porcentual al producto.
El descuento se aplica sobre el precio actual, incluyendo otros decoradores previos.
*/

public class DiscountDecorator extends MenuItemDecorator{
    private Double percentage;

    public DiscountDecorator(MenuItemComponent menuItemComponent, Double percentage){
        super(menuItemComponent);
        this.percentage = percentage;
    }

    @Override
    public String getName() {
        return menuItemComponent.getName() + " [" + (int)(percentage * 100) + "% dto.]";
    }

    @Override
    public double getPrice() {
        return menuItemComponent.getPrice() * (1 - percentage);
    }

    @Override
    public String getDescription() {
        return menuItemComponent.getDescription() + " | Discount: " + (int)(percentage * 100) + "%";
    }
}