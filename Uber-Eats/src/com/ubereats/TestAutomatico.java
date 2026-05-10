package com.ubereats;

import com.ubereats.Decorator.*;
import com.ubereats.facade.OrderFacade;
import com.ubereats.strategy.*;

import java.util.ArrayList;
import java.util.List;

public class TestAutomatico {

    private static final ServerManager serverManager = ServerManager.getInstance();
    private static final OrderFacade orderFacade = new OrderFacade(new Order());

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("   TEST AUTOMATICO — Sistema Uber Eats  ");
        System.out.println("========================================\n");

        testSingleton();
        testFactoryMethod();
        testStrategy();
        testState();
        testObserver();
        testDecorator();
        testFacade();
        testAssignDriver();
        testCancelOrder();

        System.out.println("\n========================================");
        System.out.println("  RESULTADO: " + passed + " pasados, " + failed + " fallados");
        System.out.println("========================================");
    }

    static void testSingleton() {
        printSection("SINGLETON — ServerManager");
        ServerManager instancia1 = ServerManager.getInstance();
        ServerManager instancia2 = ServerManager.getInstance();
        check("getInstance() devuelve siempre la misma instancia", instancia1 == instancia2);
    }

    static void testFactoryMethod() {
        printSection("FACTORY METHOD — Registro de usuarios");
        Client client = serverManager.registerClient("Carlos");
        check("registerClient crea un Client", client instanceof Client);
        check("Client tiene username correcto", "Carlos".equals(client.getUsername()));

        Restaurant restaurant = serverManager.registerRestaurant("Pizzeria Roma");
        check("registerRestaurant crea un Restaurant", restaurant instanceof Restaurant);

        DeliveryDriver driver = serverManager.registerDeliveryDriver("Repartidor01");
        check("registerDeliveryDriver crea un DeliveryDriver", driver instanceof DeliveryDriver);

        check("ServerManager tiene 1 cliente", serverManager.getClients().size() == 1);
        check("ServerManager tiene 1 restaurante", serverManager.getRestaurants().size() == 1);
        check("ServerManager tiene 1 repartidor", serverManager.getDeliveryDrivers().size() == 1);
    }

    static void testStrategy() {
        printSection("STRATEGY — Métodos de pago");
        PaymentMethodStrategy card = new CardPaymentStrategy("1234567812345678");
        card.pay(25.00);
        PaymentMethodStrategy paypal = new PaypalPaymentStrategy("carlos@gmail.com");
        paypal.pay(25.00);
        PaymentMethodStrategy cash = new CashPaymentStrategy();
        cash.pay(25.00);
        check("CardPaymentStrategy es PaymentMethodStrategy", card instanceof PaymentMethodStrategy);
        check("PaypalPaymentStrategy es PaymentMethodStrategy", paypal instanceof PaymentMethodStrategy);
        check("CashPaymentStrategy es PaymentMethodStrategy", cash instanceof PaymentMethodStrategy);
    }

    static void testState() {
        printSection("STATE — Ciclo de vida del pedido");
        Order order = new Order();
        check("Estado inicial es Pendiente", "Pendiente".equals(order.getOrderState().toString()));
        order.updateOrderState();
        check("Tras 1 avance: En preparación", "En preparación".equals(order.getOrderState().toString()));
        order.updateOrderState();
        check("Tras 2 avances: En reparto", "En reparto".equals(order.getOrderState().toString()));
        order.updateOrderState();
        check("Tras 3 avances: Entregado", "Entregado".equals(order.getOrderState().toString()));
        order.updateOrderState();
        check("Estado final no cambia tras otro avance", "Entregado".equals(order.getOrderState().toString()));
    }

    static void testObserver() {
        printSection("OBSERVER — Notificaciones");
        Client client     = serverManager.getClients().get(0);
        Restaurant rest   = serverManager.getRestaurants().get(0);
        DeliveryDriver dd = serverManager.getDeliveryDrivers().get(0);

        Order order = new Order();
        order.setClient(client);        // ← añadir esto
        order.setRestaurant(rest);      // ← añadir esto
        order.setDeliveryDriver(dd);    // ← añadir esto

        order.addObserver(new com.ubereats.observer.ClientObserver(client));
        order.addObserver(new com.ubereats.observer.RestaurantObserver(rest));
        order.addObserver(new com.ubereats.observer.DeliveryDriverObserver(dd));

        System.out.println("→ Notificando (estado: Pendiente):");
        order.notifyObservers();
        check("notifyObservers ejecuta sin errores", true);

        System.out.println("→ Avanzando estado (notifica automáticamente):");
        order.updateOrderState();
        check("updateOrderState notifica automáticamente", true);
    }

    static void testDecorator() {
        printSection("DECORATOR — Personalización del menú");
        MenuItemComponent item = new BasicMenuItem("Hamburguesa", 8.00, "Con lechuga y tomate");
        check("Precio base correcto", item.getPrice() == 8.00);

        item = new ExtraDecorator(item, "Queso", 1.50);
        check("ExtraDecorator suma precio", item.getPrice() == 9.50);
        check("ExtraDecorator modifica nombre", item.getName().contains("Queso"));

        item = new SizeDecorator(item, Size.LARGE);
        check("SizeDecorator LARGE aplica x1.3", Math.abs(item.getPrice() - 9.50 * 1.3) < 0.001);

        MenuItemComponent item2 = new BasicMenuItem("Pizza", 10.00, "Margarita");
        item2 = new DiscountDecorator(item2, 0.10);
        check("DiscountDecorator 10% aplica correctamente", Math.abs(item2.getPrice() - 9.00) < 0.001);

        MenuItemComponent item3 = new BasicMenuItem("Ensalada", 7.00, "César");
        item3 = new NoIngredientDecorator(item3, "Crutones", 0.50);
        check("NoIngredientDecorator resta descuento", Math.abs(item3.getPrice() - 6.50) < 0.001);

        System.out.println("→ Producto final decorado:");
        item.print();
    }

    static void testFacade() {
        printSection("FACADE — Creación de pedido completo");
        serverManager.registerDeliveryDriver("Repartidor02");

        Client client   = serverManager.getClients().get(0);
        Restaurant rest = serverManager.getRestaurants().get(0);

        List<MenuItemComponent> items = new ArrayList<>();
        items.add(new BasicMenuItem("Pizza Margarita", 10.00, "Tomate y mozzarella"));
        items.add(new ExtraDecorator(new BasicMenuItem("Hamburguesa", 8.00, "Ternera"), "Bacon", 1.00));

        PaymentMethodStrategy payment = new CardPaymentStrategy("9876543298765432");

        int ordersAntes = serverManager.getOrders().size();
        orderFacade.createOrder(client, rest, items, payment);
        int ordersDespues = serverManager.getOrders().size();

        check("Facade registra el pedido en ServerManager", ordersDespues == ordersAntes + 1);

        Order order = serverManager.getOrders().get(ordersDespues - 1);
        check("Pedido tiene cliente asignado", order.getClient() != null);
        check("Pedido tiene restaurante asignado", order.getRestaurant() != null);
        check("Pedido tiene repartidor asignado", order.getDeliveryDriver() != null);
        check("Precio total correcto", Math.abs(order.calculateTotalPrice() - 19.00) < 0.001);
    }

    static void testAssignDriver() {
        printSection("SINGLETON + FACTORY — Asignación de repartidor óptimo");
        DeliveryDriver driver = serverManager.assignDriver();
        check("Asigna el repartidor con menos pedidos (Repartidor02)",
                "Repartidor02".equals(driver.getUsername()));
    }

    static void testCancelOrder() {
        printSection("STATE — Cancelación de pedido");
        Order order = new Order();
        order.cancelOrder();
        check("Tras cancelar el estado es Cancelado",
                "Cancelado".equals(order.getOrderState().toString()));

        Order delivered = new Order();
        delivered.updateOrderState();
        delivered.updateOrderState();
        delivered.updateOrderState();
        delivered.cancelOrder();
        check("No se puede cancelar un pedido ya entregado",
                "Entregado".equals(delivered.getOrderState().toString()));
    }

    static void printSection(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    static void check(String description, boolean condition) {
        if (condition) {
            System.out.println("  ✓ " + description);
            passed++;
        } else {
            System.out.println("  ✗ FALLO: " + description);
            failed++;
        }
    }
}
