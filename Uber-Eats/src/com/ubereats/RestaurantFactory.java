package com.ubereats;

import com.ubereats.factory.UserFactory;

public class RestaurantFactory extends UserFactory {

    @Override
    protected User createUser(String username) {
        return new Restaurant(username);
    }
    
}
