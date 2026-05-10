package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.Restaurant;
import com.ubereats.state.OnDeliveryState;
import com.ubereats.state.CancelledState;

public class RestaurantObserver implements OrderObserver {
	// Restaurante al que pertenece el observador
	private Restaurant restaurant;

	// Constructor
	public RestaurantObserver(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	// Metodo para decir al restaurante de que ha cambiado el estado del pedido
	@Override
	public void update(Order order) {
		System.out.println("Restaurante: " + restaurant.getUsername() + " el pedido #" + order.getId()
				+ " del cliente " + order.getClient().getUsername() + " se ha actualizado a: "
				+ order.getOrderState());

		if (order.getOrderState() instanceof OnDeliveryState || order.getOrderState() instanceof CancelledState) {
			restaurant.removeOrder(order);
		}

	}
}
