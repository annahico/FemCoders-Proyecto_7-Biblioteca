package com.library.repository;
import java.util.List;
import com.library.model.Author;

public interface AuthorRepository {

    public Author createAuthor(Author author);

    public Author getAuthorById(int id);

    public List<Author> getAuthorByName(String name);

    public Author getAuthorByNameStrict(String name);

    public void updateAuthor(Author author);

    public void deleteAuthor(int id);

}