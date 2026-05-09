package com.ubereats;

import com.ubereats.Decorator.BasicMenuItem;
import com.ubereats.Decorator.SpecificMenuItem;

public class Restaurant extends User {
    private Menu menu;

    public Restaurant(String username) {
        super(username);
        this.menu = new Menu();
        this.menu.addMenuItem(new SpecificMenuItem(
                new BasicMenuItem("Pizza Margherita", 8.99, "Salsa de tomate, mozzarella y albahaca")));
        this.menu.addMenuItem(new SpecificMenuItem(
                new BasicMenuItem("Hamburguesa Clásica", 10.50, "Carne 100% vacuno, lechuga, tomate y queso")));
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public String toString() {
        return "Restaurant: " + getUsername();
    }

    public void printMenu() {
        menu.printMenu();
    }
}