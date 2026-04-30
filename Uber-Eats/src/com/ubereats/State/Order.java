package com.ubereats.State;
import java.util.ArrayList;
import java.util.List;

import com.ubereats.Observer.*;
import com.ubereats.Strategy.*;

public class Order implements OrderObservable{
	private OrderState orderState;
	private PaymentMethodStrategy paymentMethod;
	private List<OrderObserver> orderObservers;
	
	public Order(PaymentMethodStrategy paymentMethod) {
		this.paymentMethod = paymentMethod;
		this.orderObservers = new ArrayList<>();
	}
	
	//Es el update del uml???
	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}
	
	public OrderState getOrderState() {
		return this.orderState;
	}

	@Override
	public void addObserver(OrderObserver orderObserver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObserver(OrderObserver orderObserver) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
	}
	
	public void updateOrderState() {
		orderState.manageState(this);
	}
	
	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}
}
