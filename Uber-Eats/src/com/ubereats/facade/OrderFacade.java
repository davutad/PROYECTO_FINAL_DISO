package com.ubereats.facade;

import java.util.List;

import com.ubereats.Client;
import com.ubereats.DeliveryDriver;
import com.ubereats.MenuItem;
import com.ubereats.Order;
import com.ubereats.Restaurant;
import com.ubereats.state.PreparingState;
import com.ubereats.strategy.PaymentMethodStrategy;

public class OrderFacade {
	private final Order order;

	public OrderFacade() {
		this.order = new Order();
	}

	//FALTAN PARAMETROS :/
	public void createOrder(){
		order.setClient(selectClient()); // TODO select client

		order.setRestaurant(selectRestaurant()); // TODO select restaurante

		order.addMenuItem(selecMenuItems(order.getRestaurant())); // TODO select menu items

		order.setPaymentMethod(selectPaymentMethod()); // TODO select payment method

		order.payOrder(order.calculateTotalPrice());

		order.setOrderState(new PreparingState());
	}
}

/* 
public Order createOrder(Client client) {
		Order order = new Order(client);

		order.setRestaurant(restaurant); 
		
		for (MenuItem menuItem : menuItems) {
			order.addMenuItem(menuItem);
		}

		order.setPaymentMethod(paymentMethod);

		order.payOrder(order.calculateTotalPrice());

		order.setOrderState(new PreparingState());

		order.notifyObservers();

		DeliveryDriver assignedDriver = restaurant.assignDeliveryDriver();
		order.setDeliveryDriver(assignedDriver);

		return order;
	}*/