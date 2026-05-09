package com.ubereats;

import java.util.Scanner;

public class Test {
    private static final ServerManager serverManager = ServerManager.getInstance();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OptionsMenu optionsMenu = new OptionsMenu(serverManager, sc);

        int input = -1;
        while(input != 6){
            System.out.println("Select an option:");
            System.out.println(" 1. Register user");
            System.out.println(" 2. Delete user");
            System.out.println(" 3. Simulate new order");
            System.out.println(" 4. Update order state");
            System.out.println(" 5. Cancel order");
            System.out.println(" 6. Exit");

            input = sc.nextInt();

            switch(input){
                case 1:
                    optionsMenu.resgiterUserMenu();
                    break;
                case 2:
                    optionsMenu.deleteUserMenu();
                    break;
                case 3:
                    optionsMenu.simulateNewOrder();
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
}