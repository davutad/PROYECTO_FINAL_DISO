package com.ubereats.observer;

import com.ubereats.Order;

public class DeliveryDriverObserver implements OrderObserver{
	
	private DeliveryDriver deliveryDriver;
	
	public DeliveryDriverObserver(DeliveryDriver deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	@Override
	public void update(Order order) {
		System.out.println("Delivery driver " + deliveryDriver.getUsername() 
        + ": order status updated to: " + order.getOrderState());
		
	}

}
