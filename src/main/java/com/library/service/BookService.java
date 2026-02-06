package com.library.service;

import java.util.List;
import com.library.model.Book;

public interface BookService {
    void createBook(Book book);
    void updateBook(Book book);
    void deleteBook(String isbn);
    
    List<Book> getBooks();
    List<Book> searchTitle(String title);
    List<Book> searchAuthor(String author);
    List<Book> searchGenre(String genre);
}
