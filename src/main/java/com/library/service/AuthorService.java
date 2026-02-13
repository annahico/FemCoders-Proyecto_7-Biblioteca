package com.library.service;
import java.util.List;
import com.library.model.Author;

public interface AuthorService {
    Author createAuthorIfNotExists(String name);
    Author findById(int id);
    List<Author> findByName(String name);
}
