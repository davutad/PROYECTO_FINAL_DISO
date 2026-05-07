package com.ubereats;

import java.util.List;

public class ServerManager {

    // BDD simulada

    private List<User> users;

    private List<Restaurant> restaurants;
    private List<Client> clients;
    private List<DeliveryDriver> deliveryDrivers;


    public static ServerManager instance;

    private ServerManager() {
        restaurants = new java.util.ArrayList<>();
        clients = new java.util.ArrayList<>();
        deliveryDrivers = new java.util.ArrayList<>();
    }

    public static ServerManager getInstance() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }


}
