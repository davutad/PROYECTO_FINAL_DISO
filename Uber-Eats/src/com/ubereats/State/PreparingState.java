package com.ubereats.State;

public class PreparingState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stub
		System.out.println("El restaurante esta preparando el pedido");
		order.notifyOrderObservers(); //notificar al restaurante, repartidor y cliente
		order.setState(new OnDeliveryState());
		
	}

}
