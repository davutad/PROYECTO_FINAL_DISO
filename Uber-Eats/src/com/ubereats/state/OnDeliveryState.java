package com.ubereats.state;

import com.ubereats.Order;

public class OnDeliveryState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stub
		System.out.println("El repartidor esta en camino");
		order.setOrderState(new DeliveredState());
		
	}

	@Override
	public String toString() {
		return "En reparto";
	}

}
