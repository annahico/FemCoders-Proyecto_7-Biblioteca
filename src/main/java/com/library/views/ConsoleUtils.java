package com.library.views;

import java.util.Scanner;

public class ConsoleUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static int userOption(String prompt) {
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

    public static String stringInput(String prompt, int maxLength) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (!input.trim().isEmpty() && input.length() <= maxLength) {
                return input;
            }
            System.out.println("\u001B[31m Error: Cannot be empty or exceed " + maxLength + " characters.\u001B[0m");
        }
    }

}
