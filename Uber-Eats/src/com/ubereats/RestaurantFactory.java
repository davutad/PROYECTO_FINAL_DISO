package com.ubereats;

public class RestaurantFactory extends UserFactory {

    @Override
    protected User createUser(String username) {
        return new Restaurant(username);
    }
    
}
