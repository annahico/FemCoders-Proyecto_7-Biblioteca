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
        Author existing = repository.getAuthorByNameStrict(name);
        if (existing != null) {
            return existing;
        } else{            
        Author author = Author.builder()
                            .fullName(name)
                            .build();
        Author newAuthor = repository.createAuthor(author);
        return newAuthor;}
    }

    @Override
    public Author findById(int id) {
        return repository.getAuthorById(id);
    }

    @Override
    public List<Author> findByName(String name) {
        return repository.getAuthorByName(name);
    }
}