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


    private static ServerManager instance = new ServerManager();

    private ServerManager() {
        restaurants = new ArrayList<>();
        clients = new ArrayList<>();
        deliveryDrivers = new ArrayList<>();
    }

    public static ServerManager getInstance() {
        return instance;
    }


    // TODO refactorizar esto para que no se repita codigo, a lo mejor con enum userType o tipos genericos
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
        System.out.println("Registered Clients:");
        for (Client c : clients) {
            System.out.println("- " + c.getUsername());
        }
    }

    public void printRestaurants() {
        System.out.println("Registered Restaurants:");
        for (Restaurant r : restaurants) {
            System.out.println("- " + r.getUsername());
        }
    }

    public void printDeliveryDrivers() {
        System.out.println("Registered Delivery Drivers:");
        for (DeliveryDriver d : deliveryDrivers) {
            System.out.println("- " + d.getUsername());
        }
    }


}