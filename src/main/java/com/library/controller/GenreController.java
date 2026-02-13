package com.library.controller;
import java.util.List;
import com.library.model.Genre;
import com.library.service.GenreService;

public class GenreController {
    private final GenreService service;

    public GenreController(GenreService service) {
        this.service = service;
    }

    public Genre createGenre(String name) {
        return service.createGenreIfNotExists(name);
    }

    public List<Genre> getAllGenres() {
        return service.getAllGenres();
    }

    public Genre getGenreById(int id) {
        return service.findById(id);
    }
}
