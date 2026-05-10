package com.ubereats;

import com.ubereats.Decorator.MenuItemComponent;
import java.util.List;
import java.util.Scanner;

public class OptionsMenu {
    private final UserManagementMenu userManagementMenu;
    private final OrderCreationMenu orderCreationMenu;
    private final OrderManagementMenu orderManagementMenu;

    public OptionsMenu(ServerManager serverManager, Scanner sc) {
        this.userManagementMenu = new UserManagementMenu(serverManager, sc);
        this.orderCreationMenu = new OrderCreationMenu(serverManager, sc);
        this.orderManagementMenu = new OrderManagementMenu(serverManager, sc);
    }

    public void registerUserMenu() {
        userManagementMenu.registerUserMenu();
    }

    public void deleteUserMenu() {
        userManagementMenu.deleteUserMenu();
    }

    public void simulateNewOrder() {
        orderCreationMenu.simulateNewOrder();
    }

    public List<MenuItemComponent> chooseItemsToOrder(Restaurant restaurant) {
        return orderCreationMenu.chooseItemsToOrder(restaurant);
    }

    public void updateOrderStateMenu() {
        orderManagementMenu.updateOrderStateMenu();
    }

    public void cancelOrderMenu() {
        orderManagementMenu.cancelOrderMenu();
    }

    public void printActiveOrdersMenu() {
        orderManagementMenu.printActiveOrdersMenu();
    }

    public void printFinishedOrCancelledOrdersMenu() {
        orderManagementMenu.printFinishedOrCancelledOrdersMenu();
    }

    public void deleteFinishedOrCancelledOrdersMenu() {
        orderManagementMenu.deleteFinishedOrCancelledOrdersMenu();
    }
}