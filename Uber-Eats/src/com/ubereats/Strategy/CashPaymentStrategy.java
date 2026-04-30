package com.ubereats.Strategy;

public class CashPaymentStrategy implements PaymentMethodStrategy{

	@Override
	public void pay(Double amount) {
		System.out.println("Pagando " + amount + "€ en efectivo");
		
	}
	
}
