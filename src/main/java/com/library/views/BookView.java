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
            // CORRECCIÓN 1: Usamos getFullName() porque en Author.java es 'fullName'
            String authorsNames = (book.getAuthors() == null || book.getAuthors().isEmpty())
                    ? "Unknown Author"
                    : book.getAuthors().stream()
                            .map(Author::getFullName)
                            .collect(Collectors.joining(", "));

            // Mantenemos getName() para Genre porque en Genre.java es 'name'
            String genresNames = (book.getGenres() == null || book.getGenres().isEmpty())
                    ? "No Genre"
                    : book.getGenres().stream()
                            .map(Genre::getName)
                            .collect(Collectors.joining(", "));

            System.out.println("\u001B[34m-------------------------------------------\u001B[0m");
            System.out.printf("ID: %-5d | Title: %-20s%n", book.getId(), book.getTitle());
            System.out.printf("Author(s): %-20s | Genre: %-15s%n", authorsNames, genresNames);

            if (showFullDetails) {
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Description: " + book.getDescription());
            }
            System.out.println("\u001B[34m-------------------------------------------\u001B[0m");
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
        // CORRECCIÓN 2: Usamos .fullName() en el builder
        authors.add(Author.builder().fullName(authorName).build());

        List<Genre> genres = new ArrayList<>();
        // Genre sigue usando .name()
        genres.add(Genre.builder().name(genreName).build());

        return Book.builder()
                .title(title)
                .authors(authors)
                .genres(genres)
                .isbn(isbn)
                .description(description)
                .build();
    }

    public Book getEditBookData(Book existingBook) {
        System.out.println("\n--- EDITING: " + existingBook.getTitle() + " ---");

        String title = ConsoleUtils.stringInput("New Title [" + existingBook.getTitle() + "]: ", 200);
        if (title.isEmpty()) {
            title = existingBook.getTitle();
        }

        String authorName = ConsoleUtils.stringInput("New Author Name: ", 200);
        List<Author> authors = existingBook.getAuthors();

        if (!authorName.isEmpty()) {
            authors = new ArrayList<>();
            // CORRECCIÓN 3: Usamos .fullName() en el builder también aquí
            authors.add(Author.builder().fullName(authorName).build());
        }

        String isbn = ConsoleUtils.stringInput("New ISBN [" + existingBook.getIsbn() + "]: ", 20);
        if (isbn.isEmpty()) {
            isbn = existingBook.getIsbn();
        }

        return Book.builder()
                .id(existingBook.getId())
                .title(title)
                .authors(authors)
                .genres(existingBook.getGenres())
                .isbn(isbn)
                .description(existingBook.getDescription())
                .build();
    }

    public int askForBookId(String action) {
        System.out.println("\n--- " + action.toUpperCase() + " BOOK ---");
        return ConsoleUtils.readInt("Enter the Book ID: ", 1, 9999);
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
