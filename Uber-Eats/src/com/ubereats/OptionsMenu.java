package com.ubereats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ubereats.Decorator.MenuItem;
//Nuevos imports
import com.ubereats.Decorator.MenuItemComponent;
import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.CardPaymentStrategy;
import com.ubereats.strategy.CashPaymentStrategy;
import com.ubereats.strategy.PaypalPaymentStrategy;
import com.ubereats.strategy.PaymentMethodStrategy;

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

    public void simulateNewOrder() {
        // Antes de crear el pedido, se comprueba que existan los datos mínimos necesarios.
        if (serverManager.getClients().isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }

        if (serverManager.getRestaurants().isEmpty()) {
            System.out.println("No hay restaurantes registrados.");
            return;
        }

        if (serverManager.getDeliveryDrivers().isEmpty()) {
            System.out.println("No hay repartidores registrados.");
            return;
        }

        System.out.println("Choose a client: ");
        serverManager.printClients();

        // Se resta 1 porque el usuario elige empezando en 1, pero las listas en Java empiezan en 0.
        Client client = serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine(); // Limpia el salto de línea pendiente después de nextInt().

        System.out.println("Choose a restaurant: ");
        serverManager.printRestaurants();

        // Se obtiene el restaurante seleccionado por el usuario.
        Restaurant restaurant = serverManager.getRestaurants().get(sc.nextInt() - 1);
        sc.nextLine();

        // No tiene sentido crear un pedido si el restaurante no tiene productos disponibles.
        if (restaurant.getMenu().isEmpty()) {
            System.out.println("El restaurante no tiene productos en el menú.");
            return;
        }

        System.out.println("Choose a delivery driver: ");
        serverManager.printDeliveryDrivers();

        // Se asigna manualmente un repartidor al pedido.
        DeliveryDriver deliveryDriver = serverManager.getDeliveryDrivers().get(sc.nextInt() - 1);
        sc.nextLine();

        Order order = new Order();

        // Se asocian al pedido las entidades principales que participan en él.
        order.setClient(client);
        order.setRestaurant(restaurant);
        order.setDeliveryDriver(deliveryDriver);

        // Permite al usuario escoger los productos del menú que formarán parte del pedido.
        chooseItemsToOrder(restaurant, order);

        // Se aplica el patrón Strategy: el método de pago se decide en tiempo de ejecución.
        PaymentMethodStrategy paymentMethod = choosePaymentMethod();
        order.setPaymentMethod(paymentMethod);

        // Se valida que el pedido tenga todos los datos necesarios antes de guardarlo.
        if (!order.validate()) {
            System.out.println("No se puede crear el pedido. Faltan datos.");
            return;
        }

        // Se aplica el patrón Observer: cada entidad será avisada cuando cambie el estado del pedido.
        order.addObserver(new ClientObserver(client));
        order.addObserver(new RestaurantObserver(restaurant));
        order.addObserver(new DeliveryDriverObserver(deliveryDriver));

        // Se guarda el pedido también dentro de cada entidad relacionada.
        client.addOrder(order);
        restaurant.addOrder(order);
        deliveryDriver.addOrder(order);

        // Se añade el pedido al gestor principal de la aplicación.
        serverManager.addOrder(order);

        System.out.println("Pedido creado correctamente:");
        System.out.println(order);

        // Se calcula el precio total y se procesa el pago con la estrategia seleccionada.
        order.payOrder(order.calculateTotalPrice());

        // Primera notificación: avisa a cliente, restaurante y repartidor del estado inicial del pedido.
        order.notifyObservers();

        System.out.println();
    }

   
    public void chooseItemsToOrder(Restaurant restaurant, Order order) {
        restaurant.printMenu();

        // Bucle indefinido para permitir añadir varios productos hasta que el usuario escriba 0.
        while (true) {
            System.out.println("Enter item number to add to order (0 to finish): ");

            // Se resta 1 para adaptar la elección del usuario al índice real de la lista.
            int itemIndex = sc.nextInt() - 1;
            sc.nextLine(); // Limpia el salto de línea pendiente después de nextInt().

            // Si el usuario escribe 0, itemIndex será -1 y se termina la selección.
            if (itemIndex == -1) {
                break;
            }

            // Evita acceder a una posición inexistente del menú.
            if (itemIndex < 0 || itemIndex >= restaurant.getMenu().size()) {
                System.out.println("Invalid item number.");
                continue;
            }

            // Se obtiene el producto seleccionado y se añade al pedido.
            MenuItemComponent selectedItem = restaurant.getMenu().get(itemIndex);
            order.addMenuItem(selectedItem);

            System.out.println("Item added: " + selectedItem.getName());
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


    private PaymentMethodStrategy choosePaymentMethod() {
        System.out.println("Choose payment method:");
        System.out.println(" 1. Cash");
        System.out.println(" 2. Card");
        System.out.println(" 3. PayPal");

        int option = sc.nextInt();
        sc.nextLine();

        switch (option) {
            case 1:
                return new CashPaymentStrategy();

            case 2:
                System.out.println("Enter card number:");
                String cardNumber = sc.nextLine();
                return new CardPaymentStrategy(cardNumber);

            case 3:
                System.out.println("Enter PayPal account:");
                String paypalAccount = sc.nextLine();
                return new PaypalPaymentStrategy(paypalAccount);

            default:
                System.out.println("Invalid option. Cash selected by default.");
                return new CashPaymentStrategy();
        }
    }
}


// TODO falta metodo para enseñar todos los pedidos finalizados o cancelados y otro para los pedidos en curso, ademas podriamos tener un metodo de eliminar pedidos finalizados o cancelados de la lista de pedidos del server, aunque no se si es necesario o si simplemente se quedan ahi para tener un historial de pedidos