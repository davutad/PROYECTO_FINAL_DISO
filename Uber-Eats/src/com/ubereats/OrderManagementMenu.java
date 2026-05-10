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
        if (serverManager.getOrders().isEmpty()) {
            System.out.println("No hay pedidos registrados.\n");
            return null;
        }

        System.out.println("Choose an order: ");
        serverManager.printOrders();
        System.out.print("Enter order number: ");
        int orderIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (orderIndex < 0 || orderIndex >= serverManager.getOrders().size()) {
            System.out.println("Invalid order number.");
            return null;
        }
        return serverManager.getOrders().get(orderIndex);
    }

    public void printActiveOrdersMenu() {
        System.out.println("=== Pedidos en curso ===");

        boolean found = false;

        for (Order order : serverManager.getOrders()) {
            if (isActiveOrder(order)) {
                System.out.println(order);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No hay pedidos en curso.");
        }

        System.out.println();
    }

    public void printFinishedOrCancelledOrdersMenu() {
        System.out.println("=== Pedidos finalizados o cancelados ===");

        boolean found = false;

        for (Order order : serverManager.getOrders()) {
            if (isFinishedOrCancelledOrder(order)) {
                System.out.println(order);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No hay pedidos finalizados o cancelados.");
        }

        System.out.println();
    }

    public void deleteFinishedOrCancelledOrdersMenu() {
        List<Order> ordersToDelete = new ArrayList<>();

        for (Order order : serverManager.getOrders()) {
            if (isFinishedOrCancelledOrder(order)) {
                ordersToDelete.add(order);
            }
        }

        if (ordersToDelete.isEmpty()) {
            System.out.println("No hay pedidos finalizados o cancelados para eliminar.");
            System.out.println();
            return;
        }

        for (Order order : ordersToDelete) {
            serverManager.removeOrder(order);
        }

        System.out.println(
                "Se han eliminado " + ordersToDelete.size() + " pedidos finalizados o cancelados del servidor.");
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