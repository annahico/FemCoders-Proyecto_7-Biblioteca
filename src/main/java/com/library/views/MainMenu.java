package com.library.views;

public class MainMenu {

    public void show() {
        int option = -1;

        while (option != 0) {
            System.out.println("\n--- 📚 LIBRARY MENU ---");
            System.out.println("1. Book List");
            System.out.println("2. Add Book");
            System.out.println("3. Edit Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Search Title");
            System.out.println("6. Search Author");
            System.out.println("7. Search Genre");
            System.out.println("0. Exit");

            option = ConsoleUtils.userOption("Select: ");

            selected(option);
        }
    }

    private void selected(int opcion) {
        switch (opcion) {
            case 1 ->
                System.out.println("Listing all books...");
            case 2 ->
                System.out.println("Adding a new book...");
            case 3 ->
                System.out.println("Editing a book...");
            case 4 ->
                System.out.println("Deleting a book...");
            case 5 ->
                System.out.println("Searching by Title...");
            case 6 ->
                System.out.println("Searching by Author...");
            case 7 ->
                System.out.println("Searching by Genre...");
            case 0 ->
                System.out.println("Exiting the system... Goodbye!");
            default ->
                System.out.println("Invalid Option. Please try again.");
        }
    }

}
