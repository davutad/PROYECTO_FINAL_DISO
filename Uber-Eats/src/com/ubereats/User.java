package com.ubereats;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private static int nextId;

    static {
        nextId = 1;
    }

    // podriamos agregar email, nombre, contraseña, telefono, si fuese una bdd real
    // para esta demo vamos a usar usar username
    private int id;
    private String username;
    private List<Order> orders;

    public User(String username) {
        this.id = nextId++;
        this.username = username;
        this.orders = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
    Implementación de orders que no los usábamos hasta ahora para que el Observer no solo imprima los mensajes
    sino que pueda modificar la lista de pedidos de cada usuario y así tenga una funcionalidad lógica, no solo
    hacer Sysos y ya

    El ejemplo que me ha dado es el siguiente:

    El cliente guarda sus pedidos.
    El restaurante guarda los pedidos que tiene que preparar.
    El repartidor guarda los pedidos que tiene asignados.
    Cuando se cancela o entrega un pedido, se podría eliminar de la lista activa.
    */

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public abstract String toString();
}
