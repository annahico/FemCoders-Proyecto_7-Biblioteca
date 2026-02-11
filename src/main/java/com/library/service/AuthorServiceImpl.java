package com.library.service;

import java.util.List;
import com.library.model.Author;
import com.library.repository.AuthorRepository;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author createAuthorIfNotExists(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty");
        }

        Author existing = repository.getAuthorByName(name);
        if (existing != null) {
            return existing;
        }

        Author newAuthor = new Author(name); //consultar
        repository.createAuthor(newAuthor);
        return newAuthor;
    }

    @Override
    public List<Author> getAllAuthors() {
        return repository.getBooksbyAuthor();//consultar
    }

    @Override
    public Author findById(int id) {
        return repository.getAuthorById(id);
    }

    @Override
    public Author findByName(String name) {
        return repository.getAuthorByName(name);
    }
}
