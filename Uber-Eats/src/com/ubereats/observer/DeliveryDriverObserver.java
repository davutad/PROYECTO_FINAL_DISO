package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.User;

public class DeliveryDriverObserver implements OrderObserver{
	
	private User deliveryDriver;
	
	public DeliveryDriverObserver(User deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	@Override
	public void update(Order order) {
		System.out.println("Delivery driver " + deliveryDriver.getUsername() 
        + ": order status updated to: " + order.getOrderState());
		
	}

}
