package com.library.service;

import java.util.List;
import com.library.model.Author;

public interface AuthorService {
    Author createAuthorIfNotExists(String name);
    List<Author> getAllAuthors();
    Author findById(int id);
    Author findByName(String name);
}
