package com.ubereats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OptionsMenu {
    private final ServerManager serverManager;
    private final Scanner sc;

    public OptionsMenu(ServerManager serverManager, Scanner sc) {
        this.serverManager = serverManager;
        this.sc = sc;
    } 

    // TODO falta rellenar el menu cuando se registra un restaurante
    public void resgiterUserMenu() {
        System.out.println("Select user type to register:");
        System.out.println(" 1. Client");
        System.out.println(" 2. Restaurant");
        System.out.println(" 3. Delivery Driver");
        System.out.println(" 4. Cancel");

        int userType = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter username: ");
        String username = sc.nextLine();

        switch (userType) {
            case 1:
                serverManager.registerClient(username);
                break;
            case 2:
                serverManager.registerRestaurant(username);
                break;
            case 3:
                serverManager.registerDeliveryDriver(username);
                break;
            case 4:
                System.out.println("Operation cancelled.");
                return;
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

    //TODO aplicar facade para crear el pedido Y CREAR EL PEDIDO EN SI
    public void simulateNewOrder(){
        // TODO supongo que habra que poner un metodo add order en todos los users para que se añada el pedido a su lista de pedidos
        System.out.println("Choose a client: ");
        serverManager.printClients();
        Client c =  serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine();

        System.out.println("Choose a restaurant: ");
        serverManager.printRestaurants();
        Restaurant r = serverManager.getRestaurants().get(sc.nextInt() - 1);
        sc.nextLine();

        List<MenuItem> itemsToOrder = new ArrayList<>();
        chooseItemsToOrder(r, itemsToOrder);
    }

    public void chooseItemsToOrder(Restaurant r, List<MenuItem> itemsToOrder){
        while(true){
            System.out.println("Enter item number to add to order (0 to finish): ");
            int itemIndex = sc.nextInt() - 1;
            sc.nextLine();
            if(itemIndex == -1){
                break;
            }
            if(itemIndex < -1 || itemIndex >= r.getMenu().size()){
                System.out.println("Invalid item number.");
                continue;
            }
            itemsToOrder.add(r.getMenu().get(itemIndex));
            System.out.println("Item added: " + r.getMenu().get(itemIndex).getName());
        }
    }


    //
    public void updateOrderStateMenu(){
        System.out.println("Choose an order to update: ");
        serverManager.printOrders();
        System.out.print("Enter order number: ");
        int orderIndex = sc.nextInt() - 1;
        sc.nextLine();
        if(orderIndex < 0 || orderIndex >= serverManager.getOrders().size()){
            System.out.println("Invalid order number.");
            return;
        }
        Order orderToUpdate = serverManager.getOrders().get(orderIndex);
        orderToUpdate.updateOrderState();
        System.out.println("Order " + orderIndex + " state updated to: " + orderToUpdate.getOrderState());
        System.out.println();

    }

    public void cancelOrderMenu(){
        // TODO igual que 4 pero cancelar en vez de actualizar estado
    }
}