package com.ubereats;

public class Restaurant extends User {
    private Menu menu;

    public Restaurant(String username) {
        super(username);
        this.menu = new Menu();
    }
    
    @Override
    public String toString() {
        return "Restaurant: " + getUsername();
    }

}
