package com.ubereats;

public class Restaurant extends User {
    private Menu menu;

    public Restaurant(String name, String location) {
        super(name, location);
        this.menu = new Menu();
    }

    // TODO logica de observers
    
    @Override
    public String toString() {
        return "Restaurant: " + getName() + ", Location: " + getLocation();
    }

}
