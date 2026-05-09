package com.ubereats;

import com.ubereats.Decorator.MenuItemComponent;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItemComponent> items;

    public Menu() {
        this.items = new ArrayList<>();
    }

    public void addMenuItem(MenuItemComponent item) {
        items.add(item);
    }

    public List<MenuItemComponent> getItems() {
        return items;
    }

    public void printMenu() {
        if (items.isEmpty()) {
            System.out.println("El menú está vacío.");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName() + " - " + items.get(i).getPrice() + "$");
            System.out.println("   " + items.get(i).getDescription());
        }
    }

}

    

