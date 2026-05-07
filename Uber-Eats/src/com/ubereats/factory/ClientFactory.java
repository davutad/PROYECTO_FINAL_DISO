package com.ubereats.factory;

import com.ubereats.Client;
import com.ubereats.User;

public class ClientFactory extends UserFactory {

    @Override
    protected User createUser (String username) {
        return new Client(username);
    }
    
}
    

