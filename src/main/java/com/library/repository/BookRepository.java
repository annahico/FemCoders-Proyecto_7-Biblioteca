package com.library.repository;

import com.library.model.Book;

public interface BookRepository {

    public void createBook(Book book);

    public Book getBook(Book book);

    public Book getBookbyId (int id);

    public Book getBookbyTitle(String title);

    public Book getBookByIsbn(String isbn);

    public void updateBook(Book book);

    public void deleteBook(int id);

}
