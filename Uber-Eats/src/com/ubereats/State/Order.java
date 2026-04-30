package com.ubereats.State;
import java.util.List;

import com.ubereats.Strategy.*;

public class Order{
	private OrderState orderState;
	private PaymentMethodStrategy paymentMethod;
	private List<OrderObserver> orderObservers;
	
	public Order(PaymentMethodStrategy paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public void notifyOrderObservers() {
		
	}
	
	//Es el update del uml???
	public void setState(OrderState orderState) {
		this.orderState = orderState;
	}
	public OrderState getOrderState() {
		return this.orderState;
	}
	
	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}
	
}
