package com.ubereats.strategy;

public class PaypalPaymentStrategy implements PaymentMethodStrategy{
	
	private String paypalAccount;
	
	public PaypalPaymentStrategy(String paypalAccount) {
		this.paypalAccount = paypalAccount;
	}

	@Override
	public void pay(Double amount) {
		System.out.println("Pagando " + amount + "€ mediante PayPal desde la cuenta: " + this.paypalAccount);
	}
	
}
