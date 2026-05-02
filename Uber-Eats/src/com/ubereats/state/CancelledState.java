package com.ubereats.state;

import com.ubereats.Order;

public class CancelledState implements OrderState{

	@Override
	public void manageState(Order order) {
		System.out.println("El pedido ha sido cancelado.");
	}

}
