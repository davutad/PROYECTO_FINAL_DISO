package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.User;

public class DeliveryDriverObserver implements OrderObserver{
	
	private User deliveryDriver;
	
	public DeliveryDriverObserver(User deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	@Override
	public void update(Order order) {
		System.out.println("Delivery driver " + deliveryDriver.getUsername() 
        + ": order status updated to: " + order.getOrderState());
		// TODO falta un order ID + cliente para identificar el pedido
		// realmente el driver solo deberia recibir notificacion cuando se crea el pedido y cuando este listo para recoger
	}

}
