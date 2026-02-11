package com.library.views;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.library.model.Book;
import com.library.model.Author;
import com.library.model.Genre;

public class BookView {

    private void printBooksList(List<Book> books, boolean showFullDetails) {
        if (books == null || books.isEmpty()) {
            System.out.println("\n\u001B[31mNo books found.\u001B[0m");
            return;
        }

        for (Book book : books) {

            String authorsNames = book.getAuthors().stream()
                    .map(Author::getFullName)
                    .collect(Collectors.joining(","));

            String genresNames = book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(","));

            System.out.println("\n--------------------------");
            System.out.printf("ID: %-5d | Títtle: %-20s%n", book.getId(), book.getTitle());
            System.out.printf("Author: %-19s | Genres: %-15s%n", authorsNames, genresNames);

            if (showFullDetails) {
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Description: " + book.getDescription());
            }
            System.out.println("--------------------------");
        }
    }

    public void displayBooksBrief(List<Book> books) {
        System.out.println("\n--- Books inventory ---");
        printBooksList(books, false);
    }

    public Book getNewBookData() {
        System.out.println("\n\u001B[32m--- ADDING NEW BOOK ---\u001B[0m");
        String title = ConsoleUtils.stringInput("Title: ", 200);
        String authorName = ConsoleUtils.stringInput("Author Name: ", 200);
        String genreName = ConsoleUtils.stringInput("Genre Name: ", 50);
        String isbn = ConsoleUtils.stringInput("ISBN: ", 20);
        String description = ConsoleUtils.stringInput("Description: ", 200);

        List<Author> authors = new ArrayList<>();

        authors.add(Author.builder().fullName(authorName).build());

        List<Genre> genres = new ArrayList<>();

        genres.add(Genre.builder().name(genreName).build());

      
        return Book.builder()
                .title(title)
                .authors(authors)
                .genres(genres)
                .isbn(isbn)
                .description(description)
                .authors(authors)
                .genres(genres)
                .build();
    }

    // editar
    public Book getEditBookData(Book existingBook) {
        System.out.println("\n--- EDITANDO: " + existingBook.getTitle() + " ---");
        System.out.println("(Press enter to not change the actual value)");

        // 1. Título
        String titleInput = ConsoleUtils.stringInput("New Title [" + existingBook.getTitle() + "]: ", 200);
        String title = titleInput.isEmpty() ? existingBook.getTitle() : titleInput;

        // 2. Autor (Manejando la lista de objetos Author)
        String authorInput = ConsoleUtils.stringInput("New Author: ", 100);
        List<Author> authors = authorInput.isEmpty()
                ? existingBook.getAuthors()
                : List.of(Author.builder().fullName(authorInput).build());

        // 3. ISBN
        String isbnInput = ConsoleUtils.stringInput("New ISBN [" + existingBook.getIsbn() + "]: ", 17);
        String isbn = isbnInput.isEmpty() ? existingBook.getIsbn() : isbnInput;

        // 4. Descripción
        String descInput = ConsoleUtils.stringInput("New Description: ", 200);
        String description = descInput.isEmpty() ? existingBook.getDescription() : descInput;

        // 5. Género
        String genreInput = ConsoleUtils.stringInput("New Genre: ", 50);
        List<Genre> genres = genreInput.isEmpty()
                ? existingBook.getGenres()
                : List.of(Genre.builder().name(genreInput).build());

        return Book.builder()
                .id(existingBook.getId()) // Mantenemos el ID original
                .title(title)
                .authors(authors)
                .isbn(isbn)
                .description(description)
                .genres(genres)
                .build();
    }

    public int askForBookId(String action) {
        System.out.println("\n--- " + action.toUpperCase() + " BOOK ---");
        return ConsoleUtils.readInt("Enter the Book ID: ", 1, 9999);
    }

    public boolean confirmDeletion(String bookTitle) {
        System.out.println("\nWARNING!");
        System.out.println("Are you sure you want to delete the book: \"" + bookTitle + "\"?");

        String response = ConsoleUtils.stringInput("Type 'Y' to confirm or any other key to cancel: ", 1);

        return response.equalsIgnoreCase("Y");
    }

    public void searchBooks(List<Book> books) {
        System.out.println("\n--- Search Results ---");
        printBooksList(books, true);
    }

    public void displayBooksByGenre(List<Book> books) {
        System.out.println("\n--- Books by Genre ---");
        printBooksList(books, false);
    }
}
