package com.ubereats;

public class DeliveryDriver extends User {

    // TODO atribs especicificas de delivery driver

    public DeliveryDriver(String username) {
        super(username);
    }
    
    @Override
    public String toString() {
        return "Delivery driver: " + getUsername();
    }

}
