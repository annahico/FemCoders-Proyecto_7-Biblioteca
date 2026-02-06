package com.library.views;

import java.util.Scanner;

public class ConsoleUtils {
    private static Scanner scanner = new Scanner (System.in);

    public static int userOption(String prompt){
        System.out.println(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Please, Select a number from 1 to 7 ");
            System.out.println(prompt);
            scanner.next();
        }
        int numberSelected = scanner.nextInt();
        scanner.nextLine();
        return numberSelected;
            
        }
    
    
}
