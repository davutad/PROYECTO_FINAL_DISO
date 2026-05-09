package com.ubereats;

import com.ubereats.Decorator.BasicMenuItem;
import com.ubereats.Decorator.MenuItem;
import com.ubereats.Decorator.MenuItemComponent;
import com.ubereats.observer.*;
import com.ubereats.state.*;
import com.ubereats.strategy.*;
import java.util.ArrayList;
import java.util.List;

public class Order implements OrderObservable {

	private static int nextId = 1;

	private int id;
	private Restaurant restaurant;
	private DeliveryDriver deliveryDriver;
	private Client client;
	private List<MenuItemComponent> menuItems;

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

	//Faltaba el setter, obteniamos el repartidor pero no lo podíamos asignar a ningún pedido
	public void setDeliveryDriver(DeliveryDriver deliveryDriver) {
    	this.deliveryDriver = deliveryDriver;
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
		//Cambio en Order para no tener que estar llamando a notifyObservers en cada uno de los estados
		//Hasta ahora, no notificaba el cambio, solo lo hacía.
		notifyObservers();
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
		for (OrderObserver orderObserver : this.orderObservers) {
			orderObserver.update(this);
		}
	}

	public void updateOrderState() {
		orderState.manageState(this);
	}

	// Metodo cancelar pedido
	public void cancelOrder() {
		if (orderState instanceof DeliveredState) {
			System.out.println("No se puede cancelar un pedido ya entregado");
			return;
		}
		//Lo mismo aquí, que también notifique
		setOrderState(new CancelledState());
	}

	public void payOrder(Double amount) {
		paymentMethod.pay(amount);
	}

	public void addMenuItem(MenuItemComponent menuItem) {
		menuItems.add(menuItem);
	}

	public void setPaymentMethod(PaymentMethodStrategy paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Double calculateTotalPrice() {
		Double total = 0.0;
		for (MenuItemComponent menuItem : menuItems) {
			total += menuItem.getPrice();
		}
		return total;
	}

	public boolean validate() {
		if (client == null || restaurant == null || menuItems.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String clientName = (client != null) ? client.getUsername() : "Desconocido";
		String restName = (restaurant != null) ? restaurant.getUsername() : "Desconocido";
		return "Pedido #" + id + " | Estado: [" + orderState.getClass().getSimpleName() + "] | " +
				"Cliente: " + clientName + " | Restaurante: " + restName + " | Total: " +
				String.format("%.2f", calculateTotalPrice()) + "$";
	}
}

//Según el chati, me dice que esto ya está en OptionsMenu
//Aparte tiene sentido, porque el Order no debería encargarse del menú interactivo, sino de representar el pedido

/*
	public void chooseItemsToOrder(Restaurant r, List<BasicMenuItem> itemsToOrder){
        while(true){
            System.out.println("Enter item number to add to order (0 to finish): ");
            int itemIndex = sc.nextInt() - 1;
            sc.nextLine();
            if(itemIndex == -1){
                break;
            }
            if(itemIndex < -1 || itemIndex >= r.getMenu().size()){
                System.out.println("Invalid item number.");
                continue;
            }
            itemsToOrder.add(r.getMenu().get(itemIndex));
            System.out.println("Item added: " + r.getMenu().get(itemIndex).getName());
        }
    }
}
	*/
