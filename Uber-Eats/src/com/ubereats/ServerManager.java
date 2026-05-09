package com.ubereats;

import com.ubereats.factory.ClientFactory;
import com.ubereats.factory.DeliveryDriverFactory;
import com.ubereats.factory.RestaurantFactory;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    private ClientFactory clientFactory = new ClientFactory();
    private RestaurantFactory restaurantFactory = new RestaurantFactory();
    private DeliveryDriverFactory deliveryDriverFactory = new DeliveryDriverFactory();

    // BDD simulada
    private List<Restaurant> restaurants;
    private List<Client> clients;
    private List<DeliveryDriver> deliveryDrivers;
    private List<Order> orders;

    private static ServerManager instance = new ServerManager();

    private ServerManager() {
        restaurants = new ArrayList<>();
        clients = new ArrayList<>();
        deliveryDrivers = new ArrayList<>();
        orders = new ArrayList<>();
    }

    public static ServerManager getInstance() {
        return instance;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public List<Client> getClients() {
        return clients;
    }

    public List<DeliveryDriver> getDeliveryDrivers() {
        return deliveryDrivers;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public Client registerClient(String username) {
        Client c = (Client) clientFactory.registerUser(username);
        clients.add(c);
        return c;
    }

    public Restaurant registerRestaurant(String username) {
        Restaurant r = (Restaurant) restaurantFactory.registerUser(username);
        restaurants.add(r);
        return r;
    }

    public DeliveryDriver registerDeliveryDriver(String username) {
        DeliveryDriver d = (DeliveryDriver) deliveryDriverFactory.registerUser(username);
        deliveryDrivers.add(d);
        return d;
    }

    public void printClients() {
        for (int i = 0; i < clients.size(); i++) {
            System.out.println((i + 1) + ". " + clients.get(i).getUsername());
        }
    }

    public void printRestaurants() {
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println((i + 1) + ". " + restaurants.get(i).getUsername());
        }
    }

    public void printDeliveryDrivers() {
        for (int i = 0; i < deliveryDrivers.size(); i++) {
            System.out.println((i + 1) + ". " + deliveryDrivers.get(i).getUsername());
        }
    }

    public void printOrders() {
        if (orders.isEmpty()) {
            System.out.println("No hay pedidos registrados en el servidor.");
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            System.out.println((i + 1) + ". " + orders.get(i).toString());
        }
    }

    public void deleteClientByIndex(int index) {
        if (index >= 0 && index < clients.size())
            clients.remove(index);
    }

    public void deleteRestaurantByIndex(int index) {
        if (index >= 0 && index < restaurants.size())
            restaurants.remove(index);
    }

    public void deleteDeliveryDriverByIndex(int index) {
        if (index >= 0 && index < deliveryDrivers.size())
            deliveryDrivers.remove(index);
    }

    public DeliveryDriver assignDriver() {
        DeliveryDriver assignedDriver = null;
        
        for (DeliveryDriver driver : deliveryDrivers) {
        if (assignedDriver == null) {
            assignedDriver = driver;
        } else if (driver.getAssignedOrders() == 0) {
            // Encontramos uno con 0 pedidos, es el óptimo
            assignedDriver = driver;
            break;
        } else if (driver.getAssignedOrders() < assignedDriver.getAssignedOrders()) {
            // Tiene menos pedidos que el actual candidato
            assignedDriver = driver;
        }
    }
        return assignedDriver;
        
    }
}