package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.Client;

public class ClientObserver implements OrderObserver{
	
	private Client client;
	
	public ClientObserver(Client client) {
		this.client = client;
	}
	
	//Metodo que notifica al cliente de que su pedido ha cambiado de estado
	@Override
	public void update(Order order) {
		System.out.println("Client: " + client.getName() + " tu pedido ha cambiado a " 
	+ order.getOrderState());
		
	}

}
