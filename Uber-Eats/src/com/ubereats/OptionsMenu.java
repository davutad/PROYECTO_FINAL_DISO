package com.ubereats;

import com.ubereats.Decorator.*;
import com.ubereats.facade.OrderFacade;
import com.ubereats.strategy.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.ubereats.state.CancelledState;
import com.ubereats.state.DeliveredState;

public class OptionsMenu {
    private final ServerManager serverManager;
    private final Scanner sc;

    public OptionsMenu(ServerManager serverManager, Scanner sc) {
        this.serverManager = serverManager;
        this.sc = sc;
    }

    public void resgiterUserMenu() {
        System.out.println("Select user type to register:");
        System.out.println(" 1. Client");
        System.out.println(" 2. Restaurant");
        System.out.println(" 3. Delivery Driver");
        System.out.println(" 4. Cancel");

        int userType = sc.nextInt();
        sc.nextLine();
        
        if(userType == 4) {
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

    public void simulateNewOrder() {

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
        Client client = serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine();

        System.out.println("Choose a restaurant: ");
        serverManager.printRestaurants();
        Restaurant restaurant = serverManager.getRestaurants().get(sc.nextInt() - 1);
        sc.nextLine();

        if (restaurant.getMenu().isEmpty()) {
            System.out.println("El restaurante no tiene productos en el menú.");
            return;
        }

        Order order = new Order();
        OrderFacade orderFacade = new OrderFacade(order);

        List<MenuItemComponent> selectedItems = chooseItemsToOrder(restaurant);

        PaymentMethodStrategy paymentMethod = choosePaymentMethod();
        
        orderFacade.createOrder(client, restaurant, selectedItems, paymentMethod);

        if (!order.validate()) {
            System.out.println("No se puede crear el pedido. Faltan datos.");
            return;
        }

        System.out.println("Pedido creado correctamente:");
        System.out.println(order);

        System.out.println();
    }

   
    public List<MenuItemComponent> chooseItemsToOrder(Restaurant restaurant) {
        List<MenuItemComponent> selectedItems = new ArrayList<>();

        restaurant.printMenu();

        while (true) {
            System.out.println("Enter item number to add to order (0 to finish): ");
            int itemIndex = sc.nextInt() - 1;
            sc.nextLine();

            if (itemIndex == -1) {
                break;
            }

            if (itemIndex < 0 || itemIndex >= restaurant.getMenu().size()) {
                System.out.println("Invalid item number.");
                continue;
            }

            MenuItemComponent selectedItem = restaurant.getMenu().get(itemIndex);

            boolean decorating = true;
            while (decorating) {
                System.out.println("\nProducto actual: " + selectedItem.getName() + " | Precio: " + String.format("%.2f", selectedItem.getPrice()) + "€");
                System.out.println("¿Desea personalizar este producto?");
                System.out.println(" 1. Añadir extra");
                System.out.println(" 2. Cambiar tamaño");
                System.out.println(" 3. Quitar ingrediente");
                System.out.println(" 4. Aplicar descuento");
                System.out.println(" 5. Finalizar y añadir al pedido");

                int option = sc.nextInt();
                sc.nextLine();

                switch (option) {
                    case 1:
                        System.out.println("Nombre del extra:");
                        String extraName = sc.nextLine();
                        System.out.println("Precio del extra:");
                        double extraPrice = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new ExtraDecorator(selectedItem, extraName, extraPrice);
                        break;

                    case 2:
                        System.out.println("Seleccione tamaño:");
                        System.out.println(" 1. SMALL");
                        System.out.println(" 2. MEDIUM");
                        System.out.println(" 3. LARGE");
                        System.out.println(" 4. EXTRA_LARGE");

                        int sizeOpt = sc.nextInt();
                        sc.nextLine();

                        Size size = Size.MEDIUM;
                        if (sizeOpt == 1) {
                            size = Size.SMALL;
                        } else if (sizeOpt == 3) {
                            size = Size.LARGE;
                        } else if (sizeOpt == 4) {
                            size = Size.EXTRA_LARGE;
                        }

                        selectedItem = new SizeDecorator(selectedItem, size);
                        break;

                    case 3:
                        System.out.println("Ingrediente a quitar:");
                        String ingredient = sc.nextLine();
                        System.out.println("Descuento aplicado:");
                        double discount = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new NoIngredientDecorator(selectedItem, ingredient, discount);
                        break;

                    case 4:
                        System.out.println("Porcentaje de descuento, por ejemplo 0.15 para 15%:");
                        double percentage = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new DiscountDecorator(selectedItem, percentage);
                        break;

                    case 5:
                        decorating = false;
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            }

            selectedItems.add(selectedItem);
            System.out.println("Item final añadido: " + selectedItem.getName());
            System.out.println();
        }

        return selectedItems;
    }

    public void updateOrderStateMenu() {
        Order orderToUpdate = chooseOrder();
        if (orderToUpdate == null) {
            return;
        }
        orderToUpdate.updateOrderState();
        System.out.println();
    }

    public void cancelOrderMenu() {
        Order orderToCancel = chooseOrder();
        if (orderToCancel == null) {
            return;
        }
        orderToCancel.cancelOrder();
        System.out.println("Order cancelled.");
        System.out.println();
    }

    private Order chooseOrder() {
    	if(serverManager.getOrders().isEmpty()) {
    		System.out.println("No hay pedidos registrados.\n");
    		return null;
    	}
    	
        System.out.println("Choose an order: ");
        serverManager.printOrders();
        System.out.print("Enter order number: ");
        int orderIndex = sc.nextInt() - 1;
        sc.nextLine();
        
        if (orderIndex < 0 || orderIndex >= serverManager.getOrders().size()) {
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

    public void printActiveOrdersMenu() {
        System.out.println("=== Pedidos en curso ===");

        boolean found = false;

        for (Order order : serverManager.getOrders()) {
            if (isActiveOrder(order)) {
                System.out.println(order);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No hay pedidos en curso.");
        }

        System.out.println();
    }

    public void printFinishedOrCancelledOrdersMenu() {
        System.out.println("=== Pedidos finalizados o cancelados ===");

        boolean found = false;

        for (Order order : serverManager.getOrders()) {
            if (isFinishedOrCancelledOrder(order)) {
                System.out.println(order);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No hay pedidos finalizados o cancelados.");
        }

        System.out.println();
    }

    public void deleteFinishedOrCancelledOrdersMenu() {
        List<Order> ordersToDelete = new ArrayList<>();

        for (Order order : serverManager.getOrders()) {
            if (isFinishedOrCancelledOrder(order)) {
                ordersToDelete.add(order);
            }
        }

        if (ordersToDelete.isEmpty()) {
            System.out.println("No hay pedidos finalizados o cancelados para eliminar.");
            System.out.println();
            return;
        }

        for (Order order : ordersToDelete) {
            serverManager.removeOrder(order);
        }

        System.out.println("Se han eliminado " + ordersToDelete.size() + " pedidos finalizados o cancelados del servidor.");
        System.out.println();
    }

    private boolean isFinishedOrCancelledOrder(Order order) {
        return order.getOrderState() instanceof DeliveredState
                || order.getOrderState() instanceof CancelledState;
    }

    private boolean isActiveOrder(Order order) {
        return !isFinishedOrCancelledOrder(order);
    }

}
