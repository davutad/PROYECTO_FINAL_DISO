package com.ubereats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.CardPaymentStrategy;
import com.ubereats.strategy.CashPaymentStrategy;
import com.ubereats.strategy.PaypalPaymentStrategy;
import com.ubereats.strategy.PaymentMethodStrategy;
//Nuevos imports
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

        if (serverManager.getClients().isEmpty() || serverManager.getRestaurants().isEmpty()) {
            System.out.println("Debe haber al menos un cliente y un restaurante registrados para hacer un pedido.");

            return;
        }

        System.out.println("Choose a client: ");
        serverManager.printClients();


        // Se resta 1 porque el usuario elige empezando en 1, pero las listas en Java empiezan en 0.
        Client client = serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine(); // Limpia el salto de línea pendiente después de nextInt().

        Client c = serverManager.getClients().get(sc.nextInt() - 1);
        sc.nextLine();


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
    }

   
    public void chooseItemsToOrder(Restaurant restaurant, Order order) {
        restaurant.printMenu();

        // Bucle indefinido para permitir añadir varios productos hasta que el usuario escriba 0.

        List<MenuItemComponent> itemsToOrder = new ArrayList<>();
        chooseItemsToOrder(restaurant, itemsToOrder);

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

        Client c = new Client(null);
        Restaurant r = new Restaurant(null);
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

            // Se resta 1 para adaptar la elección del usuario al índice real de la lista.
            int itemIndex = sc.nextInt() - 1;

            sc.nextLine(); // Limpia el salto de línea pendiente después de nextInt().

            // Si el usuario escribe 0, itemIndex será -1 y se termina la selección.
            if (itemIndex == -1) {
                break;
            }

            // Evita acceder a una posición inexistente del menú.
            if (itemIndex < 0 || itemIndex >= r.getMenu().size()) {

            sc.nextLine();
            if (itemIndex == -1) {
                break;
            }
            if (itemIndex < 0 || itemIndex >= r.getMenu().size()) {

                System.out.println("Invalid item number.");
                continue;
            }

            // Se obtiene el producto seleccionado y se añade al pedido.
            MenuItemComponent selectedItem = r.getMenu().get(itemIndex);
            //.addMenuItem(selectedItem);

            System.out.println("Item added: " + selectedItem.getName());

            // Obtenemos el producto base
            selectedItem = r.getMenu().get(itemIndex);

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


// TODO falta metodo para enseñar todos los pedidos finalizados o cancelados y otro para los pedidos en curso, ademas podriamos tener un metodo de eliminar pedidos finalizados o cancelados de la lista de pedidos del server, aunque no se si es necesario o si simplemente se quedan ahi para tener un historial de pedidos

}
