package com.ubereats;

import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.CardPaymentStrategy;
import com.ubereats.strategy.PaymentMethodStrategy;
import java.util.Scanner;

public class Test {
    private static ServerManager serverManager = ServerManager.getInstance();

    public static void main(String[] args) {

        // Crear usuarios del pedido
        Restaurant restaurant = new Restaurant("Pizzeria Roma");
        Client client = new Client("Carlos");
        DeliveryDriver deliveryDriver = new DeliveryDriver("Repartidor01");

        // Crear estrategia de pago
        PaymentMethodStrategy paymentMethod = new CardPaymentStrategy("1234567812345678");

        // Crear pedido
        Order order = new Order(
            restaurant,
            deliveryDriver,
            client,
            paymentMethod
        );

        // Crear observadores
        RestaurantObserver restaurantObserver = new RestaurantObserver(restaurant);
        ClientObserver clientObserver = new ClientObserver(client);
        DeliveryDriverObserver deliveryDriverObserver = new DeliveryDriverObserver(deliveryDriver);

        // El restaurante recibe solo la notificación inicial del pedido
        restaurantObserver.update(order);

        // Cliente y repartidor reciben las actualizaciones de estado
        order.addObserver(clientObserver);
        order.addObserver(deliveryDriverObserver);

        // Pagar pedido
        order.payOrder(24.95);

        // Avanzar estados del pedido
        order.updateOrderState(); // Pendiente -> En preparación
        order.updateOrderState(); // En preparación -> En reparto
        order.updateOrderState(); // En reparto -> Entregado
        order.updateOrderState(); // Ya entregado

        /* --------------------------------------------------------------------------- */

        Scanner sc = new Scanner(System.in);

        int input = -1;
        // BUCLE MENU
        while(input != 6){
            System.out.println("1. Register new user");
            System.out.println("2. Delete user");
            System.out.println("3. Simulate new order");
            System.out.println("4. Update order state");
            System.err.println("5. Cancel order");
            System.out.println("6. Exit");

            input = sc.nextInt();

            switch(input){
                case 1:
                    resgiterUserMenu(sc);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Select a valid option.");
            }
        }

    }


    public static void resgiterUserMenu(Scanner sc){
        System.out.println("Select user type to register:");
        System.out.println("1. Client");
        System.out.println("2. Restaurant");
        System.out.println("3. Delivery Driver");
        System.out.println("4. Cancel");

        int userType = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter username:");
        String username = sc.nextLine();

        switch(userType){
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

}