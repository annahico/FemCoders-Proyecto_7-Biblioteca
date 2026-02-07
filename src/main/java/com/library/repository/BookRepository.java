package com.library.repository;

import com.library.model.Book;

public interface BookRepository {

    void createBook(Book book);

    void saveBookAuthors (Book book);

    void saveBookGenres(Book book);

    Book getBookbyId (int id);

    Book getBookbyTitle(String title);

    Book getBookByIsbn(String isbn);

    void updateBook(Book book);

    void deleteBook(int id);

    void deleteBookAuthors (int bookId);

    void deleteBookGenres (int bookId); 

}
//Faltan los select....