package com.library.controller;
import com.library.model.Author;
import com.library.service.AuthorService;

public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    public Author createAuthor(String name) {
        return service.createAuthorIfNotExists(name);
    }

    public Author getAuthorById(int id) {
        return service.findById(id);
    }
}