package com.library.views;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.library.model.Book;
import com.library.model.Author;
import com.library.model.Genre;

public class BookView {

    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String BLUE = "\u001B[34m";
    public static final String LILA = "\u001B[35;3m";
    public static final String ROSA = "\u001B[38;5;218m";
    public static final String GREEN = "\u001B[32m";

    private void printBooksList(List<Book> books, boolean showFullDetails) {
        if (books == null || books.isEmpty()) {
            System.out.println(RED + "No books found" + RESET);
            return;
        }

        for (Book book : books) {

            String authorsNames = book.getAuthors().stream()
                    .map(Author::getFullName)
                    .collect(Collectors.joining(", "));

            String genresNames = book.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining(", "));

            System.out.println(
                    BLUE + "------------------------------------------------------------------------------------------"
                            + RESET);
            System.out.printf("ID: %-5d | Títle: %-20s%n", book.getId(), book.getTitle());
            System.out.printf("Author: %-19s | Genres: %-15s%n", authorsNames, genresNames);

            if (showFullDetails) {
                System.out.println("ISBN: " + book.getIsbn());
                System.out.println("Description: " + book.getDescription());
            }
            System.out.println(
                    BLUE + "------------------------------------------------------------------------------------------"
                            + RESET);
        }
    }

    public void displayBooksBrief(List<Book> books) {
        System.out.println();
        System.out.println(ROSA + "----------- BOOKS INVENTORY ----------" + RESET);
        printBooksList(books, false);
    }

    public Book getNewBookData() {

        System.out.println(BLUE + "-------- ADDING NEW BOOK -------" + RESET);
        String title = ConsoleUtils.stringInput("Title: ", 200);

        List<Author> authors = new ArrayList<>();
        boolean addAuthors = true;

        while (addAuthors == true) {

            String authorName = ConsoleUtils.stringInput("Author Name: ", 200);

            authors.add(Author.builder().fullName(authorName).build());

            boolean validResponse = false;

            while (validResponse == false) {
                String moreAuthors = ConsoleUtils.stringInput(" Do you want to add another author (Y/N) ?", 1);
                System.out.println();

                if (moreAuthors.equalsIgnoreCase("N")) {

                    addAuthors = false;
                    validResponse = true;

                } else if (moreAuthors.equalsIgnoreCase("Y")) {
                    validResponse = true;

                } else {
                    System.out.println(RED + "Please select Y or N" + RESET);

                }

            }

        }

        List<Genre> genres = new ArrayList<>();

        boolean addGenre = true;

        while (addGenre == true) {

            String genreName = ConsoleUtils.stringInput("Genre Name: ", 50);

            genres.add(Genre.builder().name(genreName).build());

            boolean validResponse = false;

            while (validResponse == false) {
                String moreGenres = ConsoleUtils.stringInput(" Do you want to add another genre (Y/N) ?", 1);
                System.out.println();

                if (moreGenres.equalsIgnoreCase("N")) {

                    addGenre = false;
                    validResponse = true;

                } else if (moreGenres.equalsIgnoreCase("Y")) {
                    validResponse = true;

                } else {
                    System.out.println(RED + "Please select Y or N" + RESET);

                }

            }

        }

        String isbn = ConsoleUtils.stringInput("ISBN: ", 17, "^[0-9-]{10,17}$");
        String description = ConsoleUtils.stringInput("Description: ", 200);

        return Book.builder()
                .title(title)
                .authors(authors)
                .genres(genres)
                .isbn(isbn)
                .description(description)
                .build();
    }

    public Book getEditBookData(Book existingBook) {
        System.out.println("--- EDITING: " + existingBook.getTitle() + " ---");
        System.out.println("(Press enter to not change the actual value)");

        String titleInput = ConsoleUtils.stringInputEdit("New Title [" + existingBook.getTitle() + "]: ", 200);
        String title = titleInput.isEmpty() ? existingBook.getTitle() : titleInput;

        String authorInput = ConsoleUtils.stringInputEdit("New Author: ", 100);
        List<Author> authors = authorInput.isEmpty()
                ? existingBook.getAuthors()
                : List.of(Author.builder().fullName(authorInput).build());

        String isbnInput = ConsoleUtils.stringInputEdit("New ISBN [" + existingBook.getIsbn() + "]: ", 17);
        String isbn = isbnInput.isEmpty() ? existingBook.getIsbn() : isbnInput;

        String descInput = ConsoleUtils.stringInputEdit("New Description: ", 200);
        String description = descInput.isEmpty() ? existingBook.getDescription() : descInput;

        String genreInput = ConsoleUtils.stringInputEdit("New Genre: ", 50);
        List<Genre> genres = genreInput.isEmpty()
                ? existingBook.getGenres()
                : List.of(Genre.builder().name(genreInput).build());

        return Book.builder()
                .id(existingBook.getId())
                .title(title)
                .authors(authors)
                .isbn(isbn)
                .description(description)
                .genres(genres)
                .build();
    }

    public int askForBookId(String action) {
        System.out.println("--- " + action.toUpperCase() + " BOOK ---");
        return ConsoleUtils.readInt("Enter the Book ID: ", 1, 9999);
    }

    public boolean confirmDeletion(String bookTitle) {
        System.out.println(RED + "WARNING!");
        System.out.println("Are you sure you want to delete the book: \"" + bookTitle + "\"?" + RESET);

        String response = ConsoleUtils.stringInput("Type 'Y' to confirm or any other key to cancel: ", 1);

        return response.equalsIgnoreCase("Y");
    }

    public void searchBooks(List<Book> books) {
        System.out.println("--- Search Results ---");
        printBooksList(books, true);
    }

    public void displayBooksByGenre(List<Book> books) {
        System.out.println("--- Books by Genre ---");
        printBooksList(books, false);
    }
}
