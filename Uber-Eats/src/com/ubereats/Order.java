package com.ubereats;

import java.util.ArrayList;
import java.util.List;

import com.ubereats.strategy.*;
import com.ubereats.observer.*;
import com.ubereats.state.OrderState;

public class Order implements OrderObservable{
	private OrderState orderState;
	private PaymentMethodStrategy paymentMethod;
	private List<OrderObserver> orderObservers;
	
	public Order(PaymentMethodStrategy paymentMethod) {
		this.paymentMethod = paymentMethod;
		this.orderObservers = new ArrayList<>();
	}
	
	// TODO Es el update del uml???
	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}
	
	public OrderState getOrderState() {
		return this.orderState;
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
			orderObserver.update();
		}
	}
	
	public void updateOrderState() {
		orderState.manageState(this);
	}
	
	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}
}
