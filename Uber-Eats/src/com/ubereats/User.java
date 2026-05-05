package com.ubereats;

public abstract class User {
    // para esta demo podemos solo usar username
    // podriamos agregar email, nombre, contraseña, telefono, por ejemplo
    private String username;
    private List<Order> orders;

    public User(String username) {
        this.username = username;
        this.orders = new ArrayList<>();
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
