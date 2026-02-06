package com.library.views;

import java.util.Random;

public class MainMenu {

    public void show() {

        String[][] quotes = {
                { "I have always imagined that Paradise will be a kind of library.", "Jorge Luis Borges" },
                { "Books are a uniquely portable magic.", "Stephen King" },
                { "There is no friend as loyal as a book.", "Ernest Hemingway" },
                { "A room without books is like a body without a soul.", "Cicero" },
                { "When in doubt, go to the library.", "J.K. Rowling" }
        };

        Random random = new Random();
        int index = random.nextInt(quotes.length);
        String phrase = quotes[index][0];
        String author = quotes[index][1];

        int option = -1;

        while (option != 0) {

            System.out.println("\u001B[34m===========================================");
            System.out.println("       WELCOME TO LIBRARY INVENTORY");
            System.out.println("===========================================\u001B[0m");

            System.out.println("\u001B[35m\u001B[3m \"" + phrase + "\"\u001B[0m");
            System.out.println("                \u001B[35m\u001B[3m" + author + "\u001B[0m");
            System.out.println("\u001B[34m-------------------------------------------\u001B[0m");

            System.out.println("  1. Book List          5. Search Title");
            System.out.println("  2. Add Book           6. Search Author");
            System.out.println("  3. Edit Book          7. Search Genre");
            System.out.println("  4. Delete Book        0. Exit");
            System.out.println("\u001B[34m===========================================\u001B[0m");

            option = ConsoleUtils.userOption("Please Select an option: ");

            selected(option);

            System.out.println();

        }
    }

    private void selected(int opcion) {
        switch (opcion) {
            case 1 -> System.out.println("Listing all books...");
            case 2 -> System.out.println("Adding a new book...");
            case 3 -> System.out.println("Editing a book...");
            case 4 -> System.out.println("Deleting a book...");
            case 5 -> System.out.println("Searching by Title...");
            case 6 -> System.out.println("Searching by Author...");
            case 7 -> System.out.println("Searching by Genre...");
            case 0 -> System.out.println("Exiting the system... Goodbye!");
            default -> System.out.println("Invalid Option. Please try again.");
        }
    }

}
