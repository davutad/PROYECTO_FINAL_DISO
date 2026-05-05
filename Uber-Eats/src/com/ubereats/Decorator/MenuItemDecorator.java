package com.ubereats.Decorator;

/*
Clase base abstracta para todos los decoradores de productos.
Mantiene una referencia al componente original y delega en él por defecto.
*/

public abstract class MenuItemDecorator implements MenuItemComponent{
    protected MenuItemComponent menuItemComponent;

    // Constructor común para todos los decoradores.

    protected MenuItemDecorator(MenuItemComponent menuItemComponent){
        this.menuItemComponent = menuItemComponent;
    }

    @Override
    public String getName(){
        return menuItemComponent.getName();
    }
    @Override
    public double getPrice(){
        return menuItemComponent.getPrice();
    }
    @Override
    public String getDescription(){
        return menuItemComponent.getDescription();
    }

    // Información final del producto decorado

    @Override
    public void print(){
        System.out.println("===" + getName() + "===");
        System.out.println("Description: " + getDescription());
        System.out.println("Price: " + getPrice()+"$");
    }
}