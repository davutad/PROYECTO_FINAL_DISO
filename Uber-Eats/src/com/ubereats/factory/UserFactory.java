package com.ubereats.factory;

import com.ubereats.User;

public abstract class UserFactory {

    public User registerUser(String username) {
        if (checkInvalidUsername(username)) {
            throw new IllegalArgumentException("Invalid username");
        }
        User u = createUser(username);
        // TODO no se si al final hay que añadir observers a los users
        return u;
    }

    public boolean checkInvalidUsername(String username) {
        // Aqui se pueden agregar mas validaciones para un uso real
        return username.isEmpty();
    }

    protected abstract User createUser(String username);
}