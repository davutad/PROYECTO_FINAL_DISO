package com.ubereats.Decorator;

/*
Adaptador entre MenuItem y MenuItemComponent
Permite que un producto del menú pueda ser decorado más adelante
*/

public class SpecificMenuItem implements MenuItemComponent{
    private MenuItem item;

    // Recibe un producto básico del menú y lo adapta al sistema de decoradores.

    public SpecificMenuItem(MenuItem item){
        this.item =item;
    }

    @Override
    public String getName(){
        return item.getName();
    }

    @Override
    public double getPrice(){
        return item.getPrice();
    }

    @Override
    public String getDescription(){
        return item.getDescription();
    }

    // Información completa del producto

    @Override
    public void print(){
        System.out.println("===" + getName() + "===");
        System.out.println("Description: " + getDescription());
        System.out.println("Price: " + getPrice());
    }

}