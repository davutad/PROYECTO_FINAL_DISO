package com.ubereats.state;

import com.ubereats.Order;

public class PendingState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stu
		System.out.println("Pedido pendiente de confirmacion");
		order.notifyObservers();//notificar observadors
		order.setOrderState(new PreparingState());
		
	}

}
