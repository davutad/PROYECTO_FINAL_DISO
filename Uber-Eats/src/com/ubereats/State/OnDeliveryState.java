package com.ubereats.State;

public class OnDeliveryState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stub
		System.out.println("El repartidor esta en camino");
		order.notifyOrderObservers();
		order.setState(new DeliveredState());
		
	}

}
