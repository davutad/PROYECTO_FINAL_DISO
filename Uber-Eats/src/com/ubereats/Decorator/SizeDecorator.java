package com.ubereats.Decorator;

/*
Decorador que modifica el tamaño del producto.
El precio se ajusta usando el multiplicador definido en el enum Size.
*/

public class SizeDecorator extends MenuItemDecorator{
    private Size size;

    public SizeDecorator(MenuItemComponent menuItemComponent, Size size){
        super(menuItemComponent);
        this.size = size;
    }

    @Override
    public String getName() {
        return size.getLabel() + " - " + menuItemComponent.getName();
    }

    @Override
    public double getPrice() {
        return menuItemComponent.getPrice() * size.getPriceMultiplier();
    }

    @Override
    public String getDescription() {
        return menuItemComponent.getDescription() + " | Size: " + size.getLabel();
    }
}