package com.ubereats;

public class RestaurantFactory extends UserFactory {

    @Override
    protected User createUser(String name, String location) {
        return new Restaurant(name, location);
    }
    
}
