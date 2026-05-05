package com.ubereats.state;

import com.ubereats.Order;

public class DeliveredState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stub
		System.out.println("El repartidor ha entregado su pedido");
		order.notifyObservers();
	}

	@Override
	public String toString() {
		return "Entregado";
	}

}
