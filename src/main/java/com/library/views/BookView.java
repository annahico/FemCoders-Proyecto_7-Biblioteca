package com.library.views;

import java.util.List;
import com.library.model.Book; 

public class BookView {

    // Lista completa con todos los campos
    public void displayAllBooks(List<Book> books) {
        System.out.println("\n---LIBRARY INVENTORY ---");
        if (books.isEmpty()) {
            System.out.println("No books found.");
        } else {
            for (Book book : books) {
                printBookFullDetails(book);
            }
        }
    }

    // Mostrar por Género sin descripción.
    public void displayBooksByGenre(List<Book> books, String genre) {
        System.out.println("\n--- GENRE: " + genre.toUpperCase() + " ---");
        if (books.isEmpty()) {
            System.out.println("No books found for this genre.");
        } else {
            for (Book book : books) {
                System.out.println("---------------------------------------");
                System.out.println("Title: " + book.getTitle());
                // System.out.println("Author: " + book.getAuthor()); PREGUNTAR POR AUTHOR EN BOOK.JAVA 
                System.out.println("ISBN: " + book.getIsbn());
                // System.out.println("Genre: " + book.getGenre()); SAME
                
            }
        }
    }

    public void printBookFullDetails(Book book) {
        System.out.println("---------------------------------------");
        System.out.println("Title: " + book.getTitle());
        // System.out.println("Author: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        // System.out.println("Genre: " + book.getGenre());
        System.out.println("Description: " + book.getDescription());
    }

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
}