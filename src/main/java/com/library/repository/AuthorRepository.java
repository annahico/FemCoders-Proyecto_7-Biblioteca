package com.library.repository;

import com.library.model.Author;
import com.library.model.Book;

public interface AuthorRepository {

    public void createAuthor(Author author);

    public Author getAuthorById(int id);

    public Author getAuthorByName(String name);

    public Book getBooksbyAuthor(Author author); //esto no puede devolver unlibro, sino un array de objetos? solo strings?

    public void updateAuthor(Author author);

    public void deleteAuthor(int id);

}
