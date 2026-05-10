package com.ubereats;

public class DeliveryDriver extends User {
	//Atributo para tener el numero de pedidos que tiene un driver
	private Integer assignOrders;

    public DeliveryDriver(String username) {
        super(username);
        this.assignOrders = 0;
    }
    
    public int getAssignedOrders() {
    	return assignOrders;
    }
    
    //Incrementar el numero de pedidos que tiene el driver
    public void incrementAssignedOrders() {
    	this.assignOrders++;
    }
    
    //Decrementar el numero de pedidos que tiene el driver
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
