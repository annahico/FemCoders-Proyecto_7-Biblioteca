package com.library.views;
import java.util.Scanner;

public class ConsoleUtils {

    private static final Scanner scanner = new Scanner(System.in);

    public static int userOption(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("\u001B[31m Invalid input. Please enter a number.\u001B[0m");
            System.out.print(prompt);
            scanner.next();
        }
        int numberSelected = scanner.nextInt();
        scanner.nextLine();
        return numberSelected;
    }

    public static int readInt(String prompt, int min, int max) {
        int number;
        while (true) {
            number = userOption(prompt);
            if (number >= min && number <= max) {
                return number;
            }
            System.out.println("\u001B[31m Error: Number must be between " + min + " and " + max + ".\u001B[0m");
        }
    }
    
    public static String stringInputEdit(String prompt, int maxLength) {
    String input;
    while (true) {
        System.out.print(prompt); 
        input = scanner.nextLine().trim(); 

        if (input.length() <= maxLength) {
            return input;
        }
        System.out.println("\u001B[31m Error: Cannot be more than  " + maxLength + " characters.\u001B[0m");
    }
}

    public static String stringInput(String prompt, int maxLength) {
    String input;
    while (true) {
        System.out.print(prompt);
        input = scanner.nextLine().trim(); 

        if (!input.isEmpty() && input.length() <= maxLength) {
            return input;
        }
        System.out.println("\u001B[31m Error: Cannot be empty or exceed" + maxLength + " characters.\u001B[0m");
    }
}

    public static String stringInput(String prompt, int maxLength, String regex) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (!input.trim().isEmpty() && input.length() <= maxLength && input.matches(regex)) {
                return input;
            }
            System.out.println("\u001B[31m Error: Cannot be empty or exceed " + maxLength + " characters.\u001B[0m");
        }
    }

     public static String stringInputEdit(String prompt, int maxLength, String regex) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input.length() <= maxLength && input.matches(regex)) {
                return input;
            }
            System.out.println("\u001B[31m Error: Cannot be exceed " + maxLength + " characters.\u001B[0m");
        }
    }
}
