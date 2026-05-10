package com.ubereats.observer;

import com.ubereats.DeliveryDriver;
import com.ubereats.Order;
import com.ubereats.ServerManager;
import com.ubereats.state.CancelledState;
import com.ubereats.state.DeliveredState;

public class DeliveryDriverObserver implements OrderObserver {

	private DeliveryDriver deliveryDriver;

	public DeliveryDriverObserver(DeliveryDriver deliveryDriver) {
		this.deliveryDriver = deliveryDriver;
	}

	@Override
	public void update(Order order) {
		System.out.println("Delivery driver: " + deliveryDriver.getUsername()
				+ " el pedido #" + order.getId() + " del cliente " + order.getClient().getUsername() + " a cambiado a: "
				+ order.getOrderState() + "\n");

		// Aquí empezamos con los cambios de la lista de pedidos, en caso de entregado o
		// cancelado, se saca de la lista

		if (order.getOrderState() instanceof DeliveredState || order.getOrderState() instanceof CancelledState) {
			deliveryDriver.removeOrder(order);
			deliveryDriver.decrementAssignedOrders(); // Cuando el pedido se termina tambien se decrementa el numero de
														// pedidos que tiene
			ServerManager.getInstance().removeOrder(order);
		}

	}

}
