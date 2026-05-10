package com.ubereats;

import java.util.Scanner;

import com.ubereats.Decorator.BasicMenuItem;

public class Test {
    private static final ServerManager serverManager = ServerManager.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OptionsMenu optionsMenu = new OptionsMenu(serverManager, sc);

        Client carlos = serverManager.registerClient("Carlos");
        Client carmen = serverManager.registerClient("Carmen");
        Client luis = serverManager.registerClient("Luis");
        
        Restaurant kiSushi = serverManager.registerRestaurant("Ki Sushi");
        Restaurant burgerKing = serverManager.registerRestaurant("BurgerKing");
        
        DeliveryDriver driver1 = serverManager.registerDeliveryDriver("William");
        DeliveryDriver driver2 = serverManager.registerDeliveryDriver("Yandel");
        DeliveryDriver driver3 = serverManager.registerDeliveryDriver("Yilber");

        burgerKing.addMenuItem(new BasicMenuItem("Pizza Margarita", 10.00, "Tomate y mozzarella"));
        burgerKing.addMenuItem(new BasicMenuItem("Pizza Carbonara", 12.00, "Nata, bacon y champiñones"));
        burgerKing.addMenuItem(new BasicMenuItem("Ensalada César", 7.00, "Lechuga, pollo y picatostes"));
        
        kiSushi.addMenuItem(new BasicMenuItem("Nigiri salmon", 10.00, "arroz con salmon"));
        kiSushi.addMenuItem(new BasicMenuItem("Ramen Suchi", 12.00, "fideos finos con ternera"));
        kiSushi.addMenuItem(new BasicMenuItem("Pez mantequilla", 7.00, "arroz con pex matequilla"));

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