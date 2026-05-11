package com.ubereats;

import java.util.Scanner;

import com.ubereats.Decorator.BasicMenuItem;

public class Test {
    private static final ServerManager serverManager = ServerManager.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OptionsMenu optionsMenu = new OptionsMenu(serverManager, sc);

        //Harcodeado para no tener que hacerlo todo el rato
        serverManager.registerClient("Carlos");
        serverManager.registerClient("Carmen");
        serverManager.registerClient("Luis");
        
        Restaurant casaErnesto = serverManager.registerRestaurant("Casa Ernesto");
        Restaurant burgerKing = serverManager.registerRestaurant("Burger King");
        
        serverManager.registerDeliveryDriver("William");
        serverManager.registerDeliveryDriver("Yandel");
        serverManager.registerDeliveryDriver("Yilber");


        burgerKing.addMenuItem(new BasicMenuItem("CheeseBurger", 4.5, "Hambuguesa con queso y tomate"));
        burgerKing.addMenuItem(new BasicMenuItem("Nuggets", 3.00, "nuggets de pollo"));
        burgerKing.addMenuItem(new BasicMenuItem("Crispy Chicken", 5.99, "Hamburguesa de polla con tomate y lechuga"));
        
        casaErnesto.addMenuItem(new BasicMenuItem("Pasta Al Pesto", 10.00, "Pasta con pesto"));
        casaErnesto.addMenuItem(new BasicMenuItem("Pizza Margarita", 12.00, "pizza con tomate y  queso mozzarella"));
        casaErnesto.addMenuItem(new BasicMenuItem("Pizza Carbonara", 13.00, "Pizza con queso, bacon"));

        int input = -1;
        while (input != 9) {
            System.out.println("Select an option:");
            System.out.println(" 1. Register user");
            System.out.println(" 2. Delete user");
            System.out.println(" 3. Simulate new order");
            System.out.println(" 4. Update order state");
            System.out.println(" 5. Cancel order");
            System.out.println(" 6. Show active orders");
            System.out.println(" 7. Show finished/cancelled orders");
            System.out.println(" 8. Delete finished/cancelled orders");
            System.out.println(" 9. Exit");

            input = sc.nextInt();

            switch (input) {
                case 1:
                    optionsMenu.registerUserMenu();
                    break;
                case 2:
                    optionsMenu.deleteUserMenu();
                    break;
                case 3:
                    optionsMenu.simulateNewOrder();
                    break;
                case 4:
                    optionsMenu.updateOrderStateMenu();
                    break;
                case 5:
                    optionsMenu.cancelOrderMenu();
                    break;
                case 6:
                    optionsMenu.printActiveOrdersMenu();
                    break;
                case 7:
                    optionsMenu.printFinishedOrCancelledOrdersMenu();
                    break;
                case 8:
                    optionsMenu.deleteFinishedOrCancelledOrdersMenu();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Select a valid option.");
            }
        }

    }
}