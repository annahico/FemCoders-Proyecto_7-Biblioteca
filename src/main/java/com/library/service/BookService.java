package com.library.service;
import java.util.List;
import com.library.model.Book;

public interface BookService {

    void createBook(Book book);
    void updateBook(Book book);
    void deleteBook(int id);
    Book findById(int id);
    List<Book> getAllBooks();
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(String author);
    List<Book> findByGenre(String genre);
}