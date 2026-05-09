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

        // TODO crear un algoritmo para elegir un delivery driver
    }

    // TODO revisar que clase menu item a usar aqui
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
        Order orderToUpdate = chooseOrder();
        if(orderToUpdate == null){
            return;
        }
        orderToUpdate.updateOrderState();
        System.out.println("Order " + orderIndex + " state updated to: " + orderToUpdate.getOrderState());
        System.out.println();
        // TODO falta que los users a traves del observer hagan algo respecto a estos updates
    }

    public void cancelOrderMenu(){
        Order orderToCancel = chooseOrder();
        if(orderToCancel == null){
            return;
        }
        orderToCancel.cancelOrder();
        System.out.println("Order " + orderIndex + " cancelled.");
        System.out.println();
        // TODO no se que hacer si en algun momento quitar los pedidos cancelados o finalizados de la lista de pedidos del server, o si simplemente se quedan ahi
    }

    private Order chooseOrder(){
        System.out.println("Choose an order: ");
        serverManager.printOrders();
        System.out.print("Enter order number: ");
        int orderIndex = sc.nextInt() - 1;
        sc.nextLine();
        if(orderIndex < 0 || orderIndex >= serverManager.getOrders().size()){
            System.out.println("Invalid order number.");
            return null;
        }
        return serverManager.getOrders().get(orderIndex);
    }
}


// TODO falta metodo para enseñar todos los pedidos finalizados o cancelados y otro para los pedidos en curso, ademas podriamos tener un metodo de eliminar pedidos finalizados o cancelados de la lista de pedidos del server, aunque no se si es necesario o si simplemente se quedan ahi para tener un historial de pedidos