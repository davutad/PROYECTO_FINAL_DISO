package com.ubereats.facade;

import java.util.List;

import com.ubereats.Client;
import com.ubereats.Order;
import com.ubereats.Restaurant;
import com.ubereats.DeliveryDriver;
import com.ubereats.ServerManager;
import com.ubereats.Decorator.MenuItemComponent;
import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.PaymentMethodStrategy;

public class OrderFacade {
	private final Order order;

	public OrderFacade(Order order) {
		this.order = order;
	}

	public void createOrder(Client client, Restaurant restaurant, List<MenuItemComponent> menuItems, PaymentMethodStrategy paymentMethod) {
		DeliveryDriver driver = ServerManager.getInstance().assignDriver();
		
		order.setDeliveryDriver(driver);
		order.setClient(client);
		order.setRestaurant(restaurant);
		order.setPaymentMethod(paymentMethod);
		
		for (MenuItemComponent menuItem : menuItems) {
			order.addMenuItem(menuItem);
		}

		//Añadir observadores al pedido(No se como gesstionar el observador de deliveryDriver)
		order.addObserver(new ClientObserver(client));
		order.addObserver(new RestaurantObserver(restaurant));
		order.addObserver(new DeliveryDriverObserver(driver));
		
		client.addOrder(order);
        restaurant.addOrder(order);
        driver.addOrder(order);
		driver.incrementAssignedOrders();
		ServerManager.getInstance().addActiveOrders(order);

		//Pagar
		order.payOrder(order.calculateTotalPrice());

		//Se notifica a todos los observador al crear el pedido
		order.notifyObservers();
	}
	
}