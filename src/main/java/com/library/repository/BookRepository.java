package com.library.repository;

import java.util.List;

import com.library.model.Book;

public interface BookRepository {

    void createBook(Book book);

    void saveBookAuthors(Book book);

    void saveBookGenres(Book book);

    List<Book> getBookList();

    Book getBookbyId(int id);

    List<Book> getBookbyTitle(String title);

    Book getBookByIsbn(String isbn);

    List<Book> getBooksByAuthor(String name);

    List<Book> getBooksByGenre(String genre);

    void updateBook(Book book);

    void deleteBook(int id);

    void deleteBookAuthors(int bookId);

    void deleteBookGenres(int bookId);

}
//Faltan los select....
