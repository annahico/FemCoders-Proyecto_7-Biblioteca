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

           
            // option = ConsoleUtils.userOption("Select: ");

            selected(option);
        }
    }

    private void selected(int opcion) {
        if (opcion == 1) {
            System.out.println("Book list...");
        } else if (opcion == 2) {
            System.out.println("Adding Book...");
        } else if (opcion == 3) {
            System.out.println("Edditing Book...");
        }else if (opcion == 4 ) {
            System.out.println("Deleting Book");
        }else if (opcion == 5 ) {
            System.out.println("Searching Tittle");
        }else if (opcion == 6) {
            System.out.println("Searching Author");
        }else if (opcion == 7) {
            System.out.println("Searching Genre");
        }else if (opcion == 0) {
            System.out.println("Exit...");
        } else {
            System.out.println("Invalid Option.");
        }
    }

}
