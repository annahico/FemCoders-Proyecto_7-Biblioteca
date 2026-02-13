package com.library.controller;
import java.util.List;
import com.library.model.Book;
import com.library.service.BookService;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public void createBook(Book book) {
        bookService.createBook(book);
    }

    public void updateBook(Book book) {
        bookService.updateBook(book);
    }

    public void deleteBook(int id) {
        bookService.deleteBook(id);
    }

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public Book findById(int id) {
        return bookService.findById(id);
    }

    public List<Book> findByTitle(String title) {
        return bookService.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return bookService.findByAuthor(author);
    }

    public List<Book> findByGenre(String genre) {
        return bookService.findByGenre(genre);
    }
}

