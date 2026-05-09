package com.ubereats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Menu {
    Map<String, List<MenuItem>> items;

    public Menu() {
        this.items = new HashMap<>();
    }
 
    // TODO si no se usa borrar este metodo
    // public MenuItem getMenuItem(String category, String itemName) {
    //     List<MenuItem> categoryItems = items.get(category);
    //     if (categoryItems != null) {
    //         for (MenuItem item : categoryItems) {
    //             if (item.name.equals(itemName)) {
    //                 return item;
    //             }
    //         }
    //     }
    //     System.out.println("Menu item not found: " + category + " - " + itemName);
    //     return null;
    // }

    public MenuItem getMenuItemByIndex(String category, int index) {
        List<MenuItem> categoryItems = items.get(category);
        if (categoryItems != null && index >= 0 && index < categoryItems.size()) {
            return categoryItems.get(index);
        }
        System.out.println("Menu item index out of bounds: " + category + " - " + index);
        return null;
    }

    public void addCategory(String category) {
        if (!items.containsKey(category)) {
            System.out.println("Creating new category: " + category);
            items.put(category, new ArrayList<>());
        } else {
            System.out.println("Category already exists: " + category);
        }
    }

    public void addMenuItem(String category, MenuItem item) {
        if (!items.containsKey(category)) {
            System.out.println("Category does not exist: " + category);
        }

        MenuItem existingItem = getMenuItem(category, item.name);

        if (existingItem != null) {
            System.out.println("Menu item already exists: " + category + " - " + item.name);
            return;
        }

        items.get(category).add(item);
    }

    public void removeCategory(String category) {
        if (items.containsKey(category)) {
            items.remove(category);
        } else {
            System.out.println("Category does not exist: " + category);
        }
    }


    // TODO adaptar este print menu al nuevo menu item
    public void printMenu() {
        ArrayList<String> categories = new ArrayList<>(items.keySet());
        for (String category : categories) {
            System.out.println("Category: " + category);
            List<MenuItem> categoryItems = items.get(category);
            for (MenuItem item : categoryItems) {
                System.out.println("  " + item.name + " - " + item.price + "€");
                if (item.description != null) {
                    System.out.println("    " + item.description);
                }
                if (!item.extras.isEmpty()) {
                    System.out.println("    Extras:");
                    for (Map.Entry<String, Double> extra : item.extras.entrySet()) {
                        System.out.println("      " + extra.getKey() + " - €" + extra.getValue());
                    }
                }
            }
        }
    }
}


// TODO arreglar esta clase porque tenia la idea antigua de menu item