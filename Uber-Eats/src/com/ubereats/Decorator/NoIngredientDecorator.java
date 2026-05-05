package com.ubereats.Decorator;

public class NoIngredientDecorator extends MenuItemDecorator{
    private String ingredient;
    private Double discount;

    public NoIngredientDecorator(MenuItemComponent menuItemComponent, String ingredient, Double discount){
        super(menuItemComponent);
        this.ingredient = ingredient;
        this.discount = discount;
    }

    @Override
    public String getName() {
        return menuItemComponent.getName() + " (without " + ingredient + ")";
    }

    @Override
    public double getPrice() {
        return menuItemComponent.getPrice() - discount;
    }

    @Override
    public String getDescription() {
        return menuItemComponent.getDescription() + " | Without: " + ingredient;
    }
}