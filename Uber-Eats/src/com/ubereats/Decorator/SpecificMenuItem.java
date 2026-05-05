
package com.ubereats.Decorator;

public class SpecificMenuItem implements MenuItemComponent{
    private MenuItem item;

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

    @Override
    public void print(){
        System.out.println("===" + getName() + "===");
        System.out.println("Description: " + getDescription());
        System.out.println("Price: " + getPrice());
    }

}