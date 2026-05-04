package com.ubereats

public abstract class MenuItemDecorator implements MenuItemComponent{
    protected MenuItemComponent menuItemComponent;

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
    @Override
    public void print(){
        System.out.println("===" + getName() + "===");
        System.out.println("Description: " + getDescription());
        System.out.println("Price: " + getPrice()+"$");
    }
}