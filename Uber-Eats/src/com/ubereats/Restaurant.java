package com.ubereats;

import java.util.List;

import com.ubereats.Decorator.MenuItemComponent;
import java.util.ArrayList;

public class Restaurant extends User {
    private List<MenuItemComponent> menu;

    public Restaurant(String username) {
        super(username);
        this.menu = new ArrayList<MenuItemComponent>();
    }

    @Override
    public String toString() {
        return "Restaurant: " + getUsername();
    }

    public List<MenuItemComponent> getMenu() {
        return menu;
    }

    public void addMenuItem(MenuItemComponent item) {
        menu.add(item);
    }

    public void printMenu() {
        if (menu.isEmpty()) {
            System.out.println("El menú está vacío.");
            return;
        }
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + menu.get(i).getName() + " - " + menu.get(i).getPrice() + "$");
        }
    }
}