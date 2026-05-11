package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.Client;

public class ClientObserver implements OrderObserver{
	
	private Client client;
	
	public ClientObserver(Client client) {
		this.client = client;
	}
	
	@Override
	public void update(Order order) {
		System.out.println("\nClient: " + client.getName() + " tu pedido # " + order.getId() + " ha cambiado a " 
	+ order.getOrderState());
		
	}

}
