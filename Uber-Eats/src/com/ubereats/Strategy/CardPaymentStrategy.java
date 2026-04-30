package com.ubereats.Strategy;

public class CardPaymentStrategy implements PaymentMethodStrategy{

	private String cardNumber;
	
	public CardPaymentStrategy(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Override
	public void pay(Double amount) {
		//Mostrar los ultimos 4 digitos por seguridad
		String masked = "**** **** ****" + cardNumber.substring(cardNumber.length() - 4);
		System.out.println("Pagando " + amount + "€ mediante Tarjeta de credito desde la cuenta: " 
	+ masked);
		
	}

}
