package com.ubereats;

import com.ubereats.facade.OrderFacade;
import com.ubereats.observer.ClientObserver;
import com.ubereats.observer.DeliveryDriverObserver;
import com.ubereats.observer.RestaurantObserver;
import com.ubereats.strategy.CardPaymentStrategy;
import com.ubereats.strategy.PaymentMethodStrategy;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    private static ServerManager serverManager = ServerManager.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OptionsMenu menu = new OptionsMenu(serverManager, sc);

        int input = -1;
        while(input != 6){
            printoptions();

            input = sc.nextInt();

            switch(input){
                case 1:
                    menu.resgiterUserMenu();
                    break;
                case 2:
                    menu.deleteUserMenu();
                    break;
                case 3:
                    menu.simulateNewOrder();
                    break;
                case 4:
                    // TODO mostrar pedidos y elegir uno para actualizar su estado
                    break;
                case 5:
                    // TODO igual que 4 pero cancelar en vez de actualizar estado
                    break;
                
                case 6:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Select a valid option.");
            }
        }

    }

    private static void printoptions() {
        System.out.println("Select an option:");
        System.out.println(" 1. Register user");
        System.out.println(" 2. Delete user");
        System.out.println(" 3. Simulate new order");
        System.out.println(" 4. Update order state");
        System.out.println(" 5. Cancel order");
        System.out.println(" 6. Exit");
    }    
}