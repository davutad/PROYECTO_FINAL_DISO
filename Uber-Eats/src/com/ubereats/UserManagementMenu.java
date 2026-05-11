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
                addMenuItemsLoop(r);
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
    
    private void addMenuItemsLoop(Restaurant r){
        while (true) {
            System.out.println("Do you want to add a menu item? (yes/no)");
            String response = sc.nextLine().trim().toLowerCase();
            
            while (!response.equals("yes") && !response.equals("no")) {
                System.out.println("Please enter 'yes' or 'no':");
                response = sc.nextLine().trim().toLowerCase();
            }

            if (response.equals("no")) {
                break;
            }

            System.out.println("Enter item name: ");
            String itemName = sc.nextLine();

            System.out.println("Enter item price: ");
            double itemPrice = sc.nextDouble();
            sc.nextLine(); // Consume newline

            System.out.println("Enter item description: ");
            String itemDescription = sc.nextLine();

            r.addMenuItem(new BasicMenuItem(itemName, itemPrice, itemDescription));
        }
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