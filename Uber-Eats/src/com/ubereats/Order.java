package com.ubereats;

import java.util.ArrayList;
import java.util.List;

import com.ubereats.observer.*;
import com.ubereats.state.OrderState;
import com.ubereats.state.PendingState;
import com.ubereats.strategy.*;

public class Order implements OrderObservable{

	private static int nextId = 1;
	
	private int id;
	private Restaurant restaurant;
	private DeliveryDriver deliveryDriver;
	private Client client;

	private OrderState orderState;
	private PaymentMethodStrategy paymentMethod;
	private List<OrderObserver> orderObservers;
	
	public Order(Restaurant restaurant, DeliveryDriver deliveryDriver, Client client ,PaymentMethodStrategy paymentMethod) {
		this.id = nextId++;
		this.restaurant = restaurant;
		this.deliveryDriver = deliveryDriver;
		this.client = client;
		this.paymentMethod = paymentMethod;
		this.orderState = new PendingState();
		this.orderObservers = new ArrayList<>();
	}

	public int getId() {
        return id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public DeliveryDriver getDeliveryDriver() {
        return deliveryDriver;
    }

    public Client getClient() {
        return client;
    }

	public OrderState getOrderState() {
		return this.orderState;
	}

	// TODO Es el update del uml???
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
		// TODO Auto-generated method stub
		for(OrderObserver orderObserver: this.orderObservers) {
			orderObserver.update(this);
		}
	}
	
	public void updateOrderState() {
		orderState.manageState(this);
	}
	
	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}
}
