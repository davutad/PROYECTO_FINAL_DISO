package com.ubereats.factory;

import com.ubereats.Restaurant;
import com.ubereats.User;

public class RestaurantFactory extends UserFactory {

    @Override
    protected User createUser(String username) {
        return new Restaurant(username);
    }
    
}
