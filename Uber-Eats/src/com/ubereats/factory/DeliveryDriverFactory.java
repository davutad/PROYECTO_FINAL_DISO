package com.ubereats.factory;

import com.ubereats.DeliveryDriver;
import com.ubereats.User;

public class DeliveryDriverFactory extends UserFactory {

    @Override
    protected User createUser (String username) {
        return new DeliveryDriver(username);
    }
    
}
