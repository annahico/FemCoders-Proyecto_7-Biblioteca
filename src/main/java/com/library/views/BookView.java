package com.library.views;

import java.util.List;

import com.library.model.Book;

public class BookView {

    private void printBooksList(List<Book> books, boolean showFullDetails) {
        if (books.isEmpty()) {
            System.out.println("\nBooks not found.");
            return;
        }

        for (Book book : books) {
            System.out.println("\n--------------------------");
            System.out.printf("ID: %-5d | Títtle: %-20s%n", book.getId(), book.getTitle());
            // System.out.printf("Author: %-20s | Génre: %-15s%n", book.getAuthor(),
            // book.getGenre());

            // Solo mostramos la descripción si se solicita 
            if (showFullDetails) {
                System.out.println("Description: " + book.getDescription());
            }
            System.out.println("--------------------------");
        }
    }

    // Lista completa con todos los campos sin descripcion.
    public void displayBooksBrief(List<Book> books) {
        System.out.println("\n--- Books inventory ---");
        printBooksList(books, false);
    }

    // añadir libro
    public Book getNewBookData() {
        System.out.println("\n\u001B[32m--- ADDING NEW BOOK ---\u001B[0m");

        String title = ConsoleUtils.stringInput("Title: ", 200);
        // String author = ConsoleUtils.stringInput("Author: ", 100);
        String isbn = ConsoleUtils.stringInput("ISBN: ", 20);
        // String genre = ConsoleUtils.stringInput("Genre: ", 50);
        String description = ConsoleUtils.stringInput("Description: ", 200);
        return Book.builder()
                .title(title)
                .isbn(isbn)
                .description(description)
                .build();
    }

    //editar
    public Book getEditBookData(Book existingBook) {
        System.out.println("\n--- EDITANDO: " + existingBook.getTitle() + " ---");
        System.out.println("(press enter to not change the actual value)");

        String title = ConsoleUtils.stringInput("New tittle [" + existingBook.getTitle() + "]: ", 200);

        if (title.isEmpty()) {
            title = existingBook.getTitle();
        }

        String isbn = ConsoleUtils.stringInput("New ISBN [" + existingBook.getIsbn() + "]: ", 20);
        if (isbn.isEmpty()) {
            isbn = existingBook.getIsbn();
        }

        String description = ConsoleUtils.stringInput("New description: ", 200);
        if (description.isEmpty()) {
            description = existingBook.getDescription();
        }

        return Book.builder()
                .id(existingBook.getId()) /*el ID NO CAMBIA*/
                .title(title)
                .isbn(isbn)
                .description(description)
                .build();
    }

    // pregunytar id para eliminar
    public Integer askForBookId(String action) {
        System.out.println("\n--- " + action.toUpperCase() + " LIBRO ---");
        // nos aseguramos de que sea un número válido
        int id = ConsoleUtils.readInt("Introduce el ID del libro: ", 1, 9999);

        return id;
    }

    public boolean confirmDeletion(String bookTitle) {
        System.out.println("\nWARNING!");
        System.out.println("Are you sure you want to delete the book: \"" + bookTitle + "\"?");

        String response = ConsoleUtils.stringInput("Type 'Y' to confirm or any other key to cancel: ", 1);

        return response.equalsIgnoreCase("Y");
    }

    // buscar por titulo ,author
    public void searchBooks(List<Book> books) {
        System.out.println("\n--- search results ---");
        printBooksList(books, true);
    }

    // buscar por género sin descripción
    public void displayBooksByGenre(List<Book> books) {
        System.out.println("\n--- Books by genre ---");
        printBooksList(books, false);
    }

    public void displayAllBooks(List<Book> allBooks) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayAllBooks'");
    }

}
