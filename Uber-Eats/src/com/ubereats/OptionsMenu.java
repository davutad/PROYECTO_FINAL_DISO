package com.ubereats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ubereats.Decorator.*;
import com.ubereats.facade.OrderFacade;
import com.ubereats.observer.*;
import com.ubereats.strategy.*;

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
        if (serverManager.getClients().isEmpty() || serverManager.getRestaurants().isEmpty()) {
            System.out.println("Debe haber al menos un cliente y un restaurante registrados para hacer un pedido.");
            return;
        }

        System.out.println("Choose a client: ");
        serverManager.printClients();
        Client c = serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine();

        System.out.println("Choose a restaurant: ");
        serverManager.printRestaurants();
        Restaurant r = serverManager.getRestaurants().get(sc.nextInt() - 1);
        sc.nextLine();

        List<MenuItemComponent> itemsToOrder = new ArrayList<>();
        chooseItemsToOrder(r, itemsToOrder);

        if (itemsToOrder.isEmpty()) {
            System.out.println("No se han seleccionado productos. Pedido cancelado.");
            return;
        }

        // Asignar el primer repartidor disponible
        if (serverManager.getDeliveryDrivers().isEmpty()) {
            System.out.println("No hay repartidores registrados. Por favor, registre uno primero.");
            return;
        }
        DeliveryDriver driver = serverManager.getDeliveryDrivers().get(0);

        // Elegir método de pago
        System.out.println("Seleccione método de pago:");
        System.out.println(" 1. Efectivo");
        System.out.println(" 2. Tarjeta de Crédito");
        System.out.println(" 3. PayPal");
        int payOpt = sc.nextInt();
        sc.nextLine();
        PaymentMethodStrategy paymentStrategy;
        if (payOpt == 2) {
            System.out.println("Introduzca número de tarjeta: ");
            String card = sc.nextLine();
            paymentStrategy = new CardPaymentStrategy(card);
        } else if (payOpt == 3) {
            System.out.println("Introduzca cuenta de PayPal: ");
            String pp = sc.nextLine();
            paymentStrategy = new PaypalPaymentStrategy(pp);
        } else {
            paymentStrategy = new CashPaymentStrategy();
        }

        // Crear el pedido
        Order newOrder = new Order();
        newOrder.setDeliveryDriver(driver);

        // Conectar los observadores
        newOrder.addObserver(new ClientObserver(c));
        newOrder.addObserver(new RestaurantObserver(r));
        newOrder.addObserver(new DeliveryDriverObserver(driver));

        // Aplicar la fachada
        OrderFacade facade = new OrderFacade(newOrder);
        facade.createOrder(c, r, itemsToOrder, paymentStrategy);

        // Registrar en las listas globales y de usuarios
        serverManager.addOrder(newOrder);
        c.addOrder(newOrder);
        r.addOrder(newOrder);
        driver.addOrder(newOrder);

        System.out.println("Pedido creado con éxito.");
        System.out.println(newOrder);
        System.out.println();
    }

    public void chooseItemsToOrder(Restaurant r, List<MenuItemComponent> itemsToOrder) {
        r.printMenu();
        while (true) {
            System.out.println("Enter item number to add to order (0 to finish): ");
            int itemIndex = sc.nextInt() - 1;
            sc.nextLine();
            if (itemIndex == -1) {
                break;
            }
            if (itemIndex < 0 || itemIndex >= r.getMenu().size()) {
                System.out.println("Invalid item number.");
                continue;
            }

            // Obtenemos el producto base
            MenuItemComponent selectedItem = r.getMenu().get(itemIndex);

            // Menú interactivo para aplicar el patrón Decorator en tiempo real
            boolean decorating = true;
            while (decorating) {
                System.out.println("\nProducto actual: " + selectedItem.getName() + " | Precio: "
                        + String.format("%.2f", selectedItem.getPrice()) + "$");
                System.out.println("¿Desea personalizar este producto aplicando decoradores?");
                System.out.println(" 1. Añadir Extra (+ ingrediente y precio)");
                System.out.println(" 2. Cambiar Tamaño (multiplicador de precio)");
                System.out.println(" 3. Quitar Ingrediente (descuento)");
                System.out.println(" 4. Aplicar Descuento porcentual");
                System.out.println(" 5. Finalizar personalización y añadir al pedido");

                int decOpt = sc.nextInt();
                sc.nextLine();

                switch (decOpt) {
                    case 1:
                        System.out.println("Introduzca el nombre del extra (ej. Queso, Bacon): ");
                        String extraName = sc.nextLine();
                        System.out.println("Introduzca el precio del extra (ej. 1.50): ");
                        double extraPrice = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new ExtraDecorator(selectedItem, extraName, extraPrice);
                        break;
                    case 2:
                        System.out.println("Seleccione el tamaño:");
                        System.out.println(" 1. SMALL (x0.8)");
                        System.out.println(" 2. MEDIUM (x1.0)");
                        System.out.println(" 3. LARGE (x1.3)");
                        System.out.println(" 4. EXTRA_LARGE (x1.6)");
                        int sizeOpt = sc.nextInt();
                        sc.nextLine();
                        Size size = Size.MEDIUM;
                        if (sizeOpt == 1)
                            size = Size.SMALL;
                        else if (sizeOpt == 3)
                            size = Size.LARGE;
                        else if (sizeOpt == 4)
                            size = Size.EXTRA_LARGE;
                        selectedItem = new SizeDecorator(selectedItem, size);
                        break;
                    case 3:
                        System.out.println("Introduzca el ingrediente a quitar (ej. Cebolla): ");
                        String noIng = sc.nextLine();
                        System.out.println("Introduzca el descuento aplicado por quitarlo (ej. 0.50): ");
                        double noIngDisc = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new NoIngredientDecorator(selectedItem, noIng, noIngDisc);
                        break;
                    case 4:
                        System.out.println("Introduzca el porcentaje de descuento (ej. 0.15 para 15%): ");
                        double disc = sc.nextDouble();
                        sc.nextLine();
                        selectedItem = new DiscountDecorator(selectedItem, disc);
                        break;
                    case 5:
                        decorating = false;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }

            itemsToOrder.add(selectedItem);
            System.out.println("Item final añadido: " + selectedItem.getName());
            System.out.println();
        }
    }

    public void updateOrderStateMenu() {
        Order orderToUpdate = chooseOrder();
        if (orderToUpdate == null) {
            return;
        }
        orderToUpdate.updateOrderState();
        System.out.println("Order state updated to: " + orderToUpdate.getOrderState());
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
}