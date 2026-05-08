package com.ubereats.facade;

import java.util.List;

import com.ubereats.Client;
import com.ubereats.MenuItem;
import com.ubereats.Order;
import com.ubereats.Restaurant;
import com.ubereats.state.PreparingState;
import com.ubereats.strategy.PaymentMethodStrategy;

public class OrderFacade {
	private final Order order;

	public OrderFacade(Order order) {
		this.order = order;
	}

	public void createOrder(Client client, Restaurant restaurant, List<MenuItem> menuItems, PaymentMethodStrategy paymentMethod) {
		order.setClient(client);

		order.setRestaurant(restaurant);

		for (MenuItem menuItem : menuItems) {
			order.addMenuItem(menuItem);
		}

		order.setPaymentMethod(paymentMethod);

		order.payOrder(order.calculateTotalPrice());

		order.setOrderState(new PreparingState());
	}
	
	public void cancelOrder(Order order) {
		order.cancelOrder();
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
	}
*/