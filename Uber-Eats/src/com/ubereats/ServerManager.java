package com.ubereats;

import com.ubereats.factory.ClientFactory;
import com.ubereats.factory.DeliveryDriverFactory;
import com.ubereats.factory.RestaurantFactory;
import java.util.ArrayList;
import java.util.List;


// context
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
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(i + ". " + clients.get(i).getUsername());
        }
    }

    public void printRestaurants() {
        for (int i = 0; i < restaurants.size(); i++) {
            System.out.println(i + ". " + restaurants.get(i).getUsername());
        }
    }

    public void printDeliveryDrivers() {
        for (int i = 0; i < deliveryDrivers.size(); i++) {
            System.out.println(i + ". " + deliveryDrivers.get(i).getUsername());
        }
    }

    public void printOrders() {
        for (int i = 0; i < orders.size(); i++) {
            // TODO System.out.println(i + ". " + ); falta alguna manera de mostrar la info del pedido incluyendo el estado y los participantes
        }
    }

    public void deleteClientByIndex(int index) {
        if (index >= 0 && index < clients.size()) {
            clients.remove(index);
        }
    }

    public void deleteRestaurantByIndex(int index) {
        if (index >= 0 && index < restaurants.size()) {
            restaurants.remove(index);
        }
    }

    public void deleteDeliveryDriverByIndex(int index) {
        if (index >= 0 && index < deliveryDrivers.size()) {
            deliveryDrivers.remove(index);
        }
    }
}