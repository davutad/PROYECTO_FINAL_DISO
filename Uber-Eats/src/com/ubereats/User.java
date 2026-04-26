package com.ubereats;

public abstract class User {
    // para esta demo podemos solo usar username
    // podriamos agregar email, nombre, contraseña, telefono, por ejemplo
    private String username;

    public User(String username) {
        this.username = username;
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
