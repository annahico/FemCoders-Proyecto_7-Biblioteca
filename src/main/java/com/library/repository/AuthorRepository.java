package com.library.repository;

import java.util.List;

import com.library.model.Author;
import com.library.model.Book;

public interface AuthorRepository {

    public void createAuthor(Author author);

    public Author getAuthorById(int id);

    public Author getAuthorByName(String name);

    public List<Book> getBooksbyAuthor(Author author);

    public void updateAuthor(Author author);

    public void deleteAuthor(int id);

}
