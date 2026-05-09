package com.ubereats.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ubereats.Order;
import com.ubereats.ServerManager;
import com.ubereats.Decorator.BasicMenuItem;
import com.ubereats.strategy.*;

public class OrderFacade {
	private final Order order;
	private final ServerManager serverManager;
	private final Scanner sc;

	public OrderFacade(Order order, ServerManager serverManager, Scanner sc) {
		this.order = order;
		this.serverManager = serverManager;
		this.sc = sc;
	}

	public void createOrder() {
		System.out.println("Creating a new order...");
		System.out.println("Choose a client: ");
        serverManager.printClients();
        order.setClient(serverManager.getClients().get(sc.nextInt() - 1));
        sc.nextLine();

        System.out.println("Choose a restaurant: ");
        serverManager.printRestaurants();
        order.setRestaurant(serverManager.getRestaurants().get(sc.nextInt() - 1));
        sc.nextLine();

		// TODO ESTA MAL (CREO), HAY QUE HACER QUE EL CLIENTE ELIJA LOS PLATOS DEL RESTAURANTE Y QUE TAMBIEN EL DECORATOR Y TAL
		//TODO Tampoco se cual menu usar en la lista pq falta comunicacion 
        List<BasicMenuItem> itemsToOrder = new ArrayList<>();
        order.chooseItemsToOrder(order.getRestaurant(), itemsToOrder);

		order.setPaymentMethod(selectPaymentMethod(sc));
	

		order.payOrder(order.calculateTotalPrice());
	
		serverManager.addOrder(order);
		System.out.println("Order created with ID: " + order.getId());
	}

	private PaymentMethodStrategy selectPaymentMethod(Scanner sc) {
		System.out.println("Select payment method:");
		System.out.println(" 1. Credit Card");
		System.out.println(" 2. PayPal");
		System.out.println(" 3. Cash");

		int paymentChoice = sc.nextInt();
		sc.nextLine();

		while (paymentChoice < 1 || paymentChoice > 3) {
			System.out.println("Invalid choice. Please select a valid payment method:");
			System.out.println(" 1. Credit Card");
			System.out.println(" 2. PayPal");
			System.out.println(" 3. Cash");
			paymentChoice = sc.nextInt();
			sc.nextLine();
		}

		switch (paymentChoice) {
			case 1:
				System.out.println("Enter card number: ");
				String cardNumber = sc.nextLine();
				return new CardPaymentStrategy(cardNumber);
			case 2:
				System.out.println("Enter PayPal email: ");
				String email = sc.nextLine();
				return new PaypalPaymentStrategy(email);
			case 3:
				return new CashPaymentStrategy();
			default:
				throw new IllegalStateException("NO DEBERIA LLEGAR AQUI");
		}
	}
}