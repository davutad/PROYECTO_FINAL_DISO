package com.ubereats;

import com.ubereats.state.CancelledState;
import com.ubereats.state.DeliveredState;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderManagementMenu {
    private final ServerManager serverManager;
    private final Scanner sc;

    public OrderManagementMenu(ServerManager serverManager, Scanner sc) {
        this.serverManager = serverManager;
        this.sc = sc;
    }

    public void updateOrderStateMenu() {
        Order orderToUpdate = chooseOrder();
        if (orderToUpdate == null) {
            return;
        }
        orderToUpdate.updateOrderState();
        System.out.println();
    }

    public void cancelOrderMenu() {
        Order orderToCancel = chooseOrder();
        if (orderToCancel == null) {
            return;
        }
        orderToCancel.cancelOrder();
        System.out.println("Order cancelled.");
        System.out.println();
    }

    private Order chooseOrder() {
        if (serverManager.getActiveOrders().isEmpty()) {
            System.out.println("No hay pedidos registrados.\n");
            return null;
        }

        System.out.println("Choose an order: ");
        serverManager.printOrders();
        System.out.print("Enter order number: ");
        int orderIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (orderIndex < 0 || orderIndex >= serverManager.getActiveOrders().size()) {
            System.out.println("Invalid order number.");
            return null;
        }
        return serverManager.getActiveOrders().get(orderIndex);
    }

    //Imprimimos los pedidos de la lista de pedidos activos
    public void printActiveOrdersMenu() {
    	if(serverManager.getActiveOrders().isEmpty()) {
    		System.out.println("No hay pedidos activos.\n");
    		return;
    	}
        System.out.println("=== Pedidos en curso ===");

        for (Order order : serverManager.getActiveOrders()) {
            if (isActiveOrder(order)) {
                System.out.println(order);
            }
        }

        System.out.println();
    }

    //Ahora miramos en la lista de pedidos cancelados
    public void printFinishedOrCancelledOrdersMenu() {
        if(serverManager.getFinishedOrders().isEmpty()) {
        	System.out.println("No hay pedidos finalizados o cancelados.");
        	return;
        }

        System.out.println("=== Pedidos finalizados o cancelados ===");

        for (Order order : serverManager.getFinishedOrders()) {
            if (isFinishedOrCancelledOrder(order)) {
                System.out.println(order);
                
            }
        }

        System.out.println();
    }

    public void deleteFinishedOrCancelledOrdersMenu() {
        if(serverManager.getFinishedOrders().isEmpty()) {
        	System.out.println("No hay pedidos finalizados o cancelados para eliminar.");
        	return;
        }

        for (Order order : serverManager.getFinishedOrders()) {
            serverManager.removeFinishedOrders(order);
        }

        System.out.println(
                "Se han eliminado " + serverManager.getFinishedOrders().size() + " pedidos finalizados o cancelados del servidor.");
        System.out.println();
    }

    private boolean isFinishedOrCancelledOrder(Order order) {
        return order.getOrderState() instanceof DeliveredState
                || order.getOrderState() instanceof CancelledState;
    }

    private boolean isActiveOrder(Order order) {
        return !isFinishedOrCancelledOrder(order);
    }
}