package com.ubereats.facade;

import java.util.List;

import com.ubereats.Client;
import com.ubereats.Order;
import com.ubereats.Restaurant;
import com.ubereats.Decorator.MenuItemComponent;
import com.ubereats.strategy.PaymentMethodStrategy;

public class OrderFacade {
	private final Order order;

	public OrderFacade(Order order) {
		this.order = order;
	}

	public void createOrder(Client client, Restaurant restaurant, List<MenuItemComponent> menuItems, PaymentMethodStrategy paymentMethod) {
		order.setClient(client);

		order.setRestaurant(restaurant);

		for (MenuItemComponent menuItem : menuItems) {
			order.addMenuItem(menuItem);
		}

		order.setPaymentMethod(paymentMethod);

		order.payOrder(order.calculateTotalPrice());

		order.updateOrderState();

		
	}

	public void cancelOrder(Order order) {
		order.cancelOrder();
	}
}

/*
 * public Order createOrder(Client client) {
 * Order order = new Order(client);
 * 
 * order.setRestaurant(restaurant);
 * 
 * for (MenuItem menuItem : menuItems) {
 * order.addMenuItem(menuItem);
 * }
 * 
 * order.setPaymentMethod(paymentMethod);
 * 
 * order.payOrder(order.calculateTotalPrice());
 * 
 * order.setOrderState(new PreparingState());
 * 
 * order.notifyObservers();
 * 
 * DeliveryDriver assignedDriver = restaurant.assignDeliveryDriver();
 * order.setDeliveryDriver(assignedDriver);
 * 
 * return order;
 * }
 */