package com.ubereats;

import com.ubereats.Decorator.BasicMenuItem;
import java.util.Scanner;

public class UserManagementMenu {
    private final ServerManager serverManager;
    private final Scanner sc;

    public UserManagementMenu(ServerManager serverManager, Scanner sc) {
        this.serverManager = serverManager;
        this.sc = sc;
    }

    public void registerUserMenu() {
        System.out.println("Select user type to register:");
        System.out.println(" 1. Client");
        System.out.println(" 2. Restaurant");
        System.out.println(" 3. Delivery Driver");
        System.out.println(" 4. Cancel");

        int userType = sc.nextInt();
        sc.nextLine();

        if (userType == 4) {
            System.out.println("Operation cancelled.");
            return;
        }

        System.out.println("Enter username: ");
        String username = sc.nextLine();

        switch (userType) {
            case 1:
                serverManager.registerClient(username);
                break;
            case 2:
                Restaurant r = serverManager.registerRestaurant(username);
                // Rellenamos el menú por defecto al registrar un restaurante
                r.addMenuItem(
                        new BasicMenuItem("Hamburguesa Clásica", 8.50, "Hamburguesa de ternera con lechuga y tomate"));
                r.addMenuItem(new BasicMenuItem("Pizza Margarita", 10.00, "Pizza con salsa de tomate y mozzarella"));
                r.addMenuItem(
                        new BasicMenuItem("Ensalada César", 7.00, "Ensalada con pollo, picatostes y salsa césar"));
                break;
            case 3:
                serverManager.registerDeliveryDriver(username);
                break;

            default:
                System.out.println("Invalid user type.");
                return;
        }

        System.out.println("User registered: " + username);
        System.out.println();
    }

    public void deleteUserMenu() {
        System.out.println("Select user type to delete:");
        System.out.println(" 1. Client");
        System.out.println(" 2. Restaurant");
        System.out.println(" 3. Delivery Driver");
        System.out.println(" 4. Cancel");

        int userType = sc.nextInt();
        sc.nextLine();

        switch (userType) {
            case 1:
                System.out.println("Choose a client to delete: ");
                serverManager.printClients();
                int clientIndex = sc.nextInt() - 1;
                sc.nextLine();
                serverManager.deleteClientByIndex(clientIndex);
                System.out.println("Client deleted.");
                System.out.println();
                break;
            case 2:
                System.out.println("Choose a restaurant to delete: ");
                serverManager.printRestaurants();
                int restaurantIndex = sc.nextInt() - 1;
                sc.nextLine();
                serverManager.deleteRestaurantByIndex(restaurantIndex);
                System.out.println("Restaurant deleted.");
                System.out.println();
                break;
            case 3:
                System.out.println("Choose a delivery driver to delete: ");
                serverManager.printDeliveryDrivers();
                int driverIndex = sc.nextInt() - 1;
                sc.nextLine();
                serverManager.deleteDeliveryDriverByIndex(driverIndex);
                System.out.println("Delivery driver deleted.");
                System.out.println();
                break;
            case 4:
                System.out.println("Operation cancelled.");
                return;
            default:
                System.out.println("Invalid user type.");
        }

        System.out.println();
    }
}