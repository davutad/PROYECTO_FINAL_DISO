package com.ubereats.state;

import com.ubereats.Order;

public class PreparingState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stub
		System.out.println("El restaurante esta preparando el pedido");
		order.notifyObservers(); //notificar al restaurante, repartidor y cliente
		order.setOrderState(new OnDeliveryState());
		
	}

}
