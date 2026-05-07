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

    @Override
    public abstract String toString();
}
