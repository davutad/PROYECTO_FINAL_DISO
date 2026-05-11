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
    
    //Listas para guardar los pedidos activos y finalizados, asi es mejor para luego gestionarlos
    private List<Order> activeOrders;
    private List<Order> finishedOrders;

    private static ServerManager instance = new ServerManager();

    private ServerManager() {
        restaurants = new ArrayList<>();
        clients = new ArrayList<>();
        deliveryDrivers = new ArrayList<>();
        activeOrders = new ArrayList<>();
        finishedOrders = new ArrayList<>();       
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

    public List<Order> getActiveOrders(){
    	return activeOrders;
    }
    
    public List<Order> getFinishedOrders(){
    	return finishedOrders;
    }

    public void addActiveOrders(Order order) {
        activeOrders.add(order);
    }
    
    public void addFinishedOrders(Order order) {
        finishedOrders.add(order);
    }

    public void removeActiveOrders(Order order) {
        activeOrders.remove(order);
    }
    
    public void removeFinishedOrders(Order order) {
        finishedOrders.remove(order);
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
        if (activeOrders.isEmpty()) {
            System.out.println("No hay pedidos registrados en el servidor.");
            return;
        }
        for (int i = 0; i < activeOrders.size(); i++) {
            System.out.println((i + 1) + ". " + activeOrders.get(i).toString());
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
        if (deliveryDrivers.isEmpty())
            return null;

        DeliveryDriver bestDriver = deliveryDrivers.get(0);
        for (DeliveryDriver driver : deliveryDrivers) {
            if (driver.getAssignedOrders() < bestDriver.getAssignedOrders()) {
                bestDriver = driver;
            }
        }
        return bestDriver;
    }
    
    public void archiveOrder(Order order) {
    	activeOrders.remove(order);
    	finishedOrders.add(order);
    }
}