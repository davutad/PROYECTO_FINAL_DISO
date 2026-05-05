package com.ubereats;

public class Client extends User {

    public Client(String username) {
        super(username);
    }

    public String getName() {
        return getUsername();
    }

    @Override
    public String toString() {
        return "Client: " + getUsername();
    }
}