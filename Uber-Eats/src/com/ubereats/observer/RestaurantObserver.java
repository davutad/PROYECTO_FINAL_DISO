package com.ubereats.observer;

import com.ubereats.Order;
import com.ubereats.Restaurant;

public class RestaurantObserver implements OrderObserver{
	//Restaurante al que pertenece el observador
	private Restaurant restaurant;
	
	//Constructor
	public RestaurantObserver(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	//Metodo para decir al restaurante de que ha cambiado el estado del pedido
	@Override
	public void update(Order order) {
		System.out.println("Restaurante: " + restaurant.getUsername() + "notificado. Pedido #" + order.getId() + 
		" del cliente " + order.getClient().getName() + "Nuevo estado: " + order.getOrderState());
		// TODO falta un order ID + cliente para identificar el pedido
		// realmente el restaurante solo deberia recibir notificacion cuando se crea el pedido, luego solo actualiza
	}
}
