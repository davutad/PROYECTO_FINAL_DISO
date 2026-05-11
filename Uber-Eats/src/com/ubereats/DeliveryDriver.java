package com.ubereats;

public class DeliveryDriver extends User {
	private Integer assignOrders;

    public DeliveryDriver(String username) {
        super(username);
        this.assignOrders = 0;
    }
    
    public Integer getAssignedOrders() {
    	return assignOrders;
    }

    public void incrementAssignedOrders() {
    	this.assignOrders++;
    }
    
    public void decrementAssignedOrders() {
    	if(assignOrders > 0) {
    		this.assignOrders--;
    	}
    }
    
    @Override
    public String toString() {
        return "Delivery driver: " + getUsername();
    }

}
