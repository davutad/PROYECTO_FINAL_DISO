package com.ubereats;

import com.ubereats.observer.*;
import com.ubereats.state.OrderState;
import com.ubereats.state.PendingState;
import com.ubereats.strategy.*;
import java.util.ArrayList;
import java.util.List;

public class Order implements OrderObservable{

	private static int nextId = 1;
	
	private int id;
	private Restaurant restaurant;
	private DeliveryDriver deliveryDriver;
	private Client client;
	private List<MenuItem> menuItems;

	private OrderState orderState;
	private PaymentMethodStrategy paymentMethod;
	private List<OrderObserver> orderObservers;

	
	public Order() {
		this.id = nextId++;
		this.menuItems = new ArrayList<>();
		this.orderState = new PendingState();
		this.orderObservers = new ArrayList<>();
	}

	public int getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;	
	}
	
    public DeliveryDriver getDeliveryDriver() {
        return deliveryDriver;
    }

    public Client getClient() {
        return client;
    }

	public void setClient(Client client) {
		this.client = client;
	}
	
	public OrderState getOrderState() {
		return this.orderState;
	}


	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}
	

	@Override
	public void addObserver(OrderObserver orderObserver) {
		orderObservers.add(orderObserver);
		
	}

	@Override
	public void deleteObserver(OrderObserver orderObserver) {
		orderObservers.remove(orderObserver);
		
	}
	
	@Override
	public void notifyObservers() {
		for(OrderObserver orderObserver: this.orderObservers) {
			orderObserver.update(this);
		}
	}
	
	public void updateOrderState() {
		orderState.manageState(this);
	}
	
	// TODO creo que falta un cancelar pedido

	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}

	public void addMenuItem(MenuItem menuItem) {
		menuItems.add(menuItem);
	}
	

	public void setPaymentMethod(PaymentMethodStrategy paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", restaurant=" + restaurant + ", deliveryDriver=" + deliveryDriver + ", client="
				+ client + ", menuItems=" + menuItems + ", orderState=" + orderState + ", paymentMethod="
				+ paymentMethod + "]";
	}

	public Double calculateTotalPrice() {
		Double total = 0.0;
		for (MenuItem menuItem : menuItems) {
			total += menuItem.price;
		}
		return total;
	}

	// TODO falta un metodo "validate" que vea que todos los campos necesarios de pedido no sean nulos
	public Boolean validateOrder() {
		if(this.getRestaurant() != null && this.getClient() != null && this.getDeliveryDriver() != null) {
			return true;
		}else {
			return false;
		}
	
	}
}
