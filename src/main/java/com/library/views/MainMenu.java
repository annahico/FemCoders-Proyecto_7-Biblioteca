package com.library.views;

import java.util.List;
import java.util.Random;

import com.library.model.Book;
import com.library.controller.BookController;

public class MainMenu {
    private BookView bookView = new BookView();
    private BookController controller;

    public MainMenu(BookController controller) {
        this.controller = controller;
    }

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

            option = ConsoleUtils.readInt("Please Select an option: ", 0, 7);

            selected(option);

            System.out.println();

        }
    }

    private void selected(int opcion) {
        switch (opcion) {
            case 1 -> {
                System.out.println("Listing all books...");
                List<Book> allBooks = controller.getAllBooks();
                bookView.displayBooksBrief(allBooks);
            }
            case 2 -> {
                Book newBook = bookView.getNewBookData();
                controller.createBook(newBook);
                System.out.println("Book saved successfully!");
            }
            case 3 -> {
                int id = bookView.askForBookId("Edit");
                Book BookToEdit = controller.findById(id);

                if (BookToEdit != null) {
                    Book updatedBook = bookView.getEditBookData(BookToEdit);
                    controller.updateBook(updatedBook);
                    System.out.println("Book updated successfully!");
                } else {
                    System.out.println("Book not found with ID: " + id);
                }

                System.out.println("Editing logic ready, waiting for service.findById()");
            }
            case 4 -> {
                int id = bookView.askForBookId("Delete");
                System.out.println("Processing deletion for ID: " + id);
            }

            case 5 -> {
                String title = ConsoleUtils.stringInput("Enter Title to search: ", 200);
                List<Book> results = controller.findByTitle(title);
                bookView.searchBooks(results);
            }
            case 6 -> {
                String author = ConsoleUtils.stringInput("Enter Author to search: ", 100);
                List<Book> results = controller.findByAuthor(author);
                bookView.searchBooks(results);
                System.out.println("Searching for books by: " + author);
            }
            case 7 -> {
                String genre = ConsoleUtils.stringInput("Enter Genre to search: ", 50);
                List<Book> results = controller.findByGenre(genre);
                bookView.displayBooksByGenre(results);
            }

            case 0 -> System.out.println("Exiting the system... Goodbye!");
            default -> System.out.println("Invalid Option. Please try again.");
        }
    }

}
