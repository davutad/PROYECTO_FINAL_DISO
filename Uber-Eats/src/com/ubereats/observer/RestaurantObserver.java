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
		System.out.println("Restaurante: " + restaurant.getUsername() + "notificado. Nuevo estado: " 
	+ order.getOrderState());
		// TODO cuando el estado ya sea preparado, el restaurante podria eliminar el pedido de su lista de pedidos activos
	}
}
