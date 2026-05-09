package com.ubereats.Decorator;

public class NoIngredientDecorator extends MenuItemDecorator {
    private String ingredient;
    private Double discount;

    public NoIngredientDecorator(MenuItemComponent menuItemComponent, String ingredient, Double discount) {
        super(menuItemComponent);
        this.ingredient = ingredient;
        this.discount = discount;
    }

    @Override
    public String getName() {
        return menuItemComponent.getName() + " (sin " + ingredient + ")";
    }

    @Override
    public double getPrice() {
        double newPrice = menuItemComponent.getPrice() - discount;
        return newPrice < 0 ? 0 : newPrice;
    }

    @Override
    public String getDescription() {
        return menuItemComponent.getDescription() + " | Without: " + ingredient;
    }
}