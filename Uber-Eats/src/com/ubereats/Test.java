package com.ubereats;

import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.CardPaymentStrategy;
import com.ubereats.strategy.PaymentMethodStrategy;

public class Test {

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
    }
}