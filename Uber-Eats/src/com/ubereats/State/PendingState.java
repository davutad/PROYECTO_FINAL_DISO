package com.ubereats.State;

public class PendingState implements OrderState{

	@Override
	public void manageState(Order order) {
		// TODO Auto-generated method stu
		System.out.println("Pedido pendiente de confirmacion");
		order.notifyOrderObservers();//notificar observadors
		order.setState(new PreparingState());
		
	}

}
