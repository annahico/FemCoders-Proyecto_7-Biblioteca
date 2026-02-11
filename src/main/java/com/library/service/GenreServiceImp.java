package com.library.service;

import java.util.List;
import com.library.model.Genre;
import com.library.repository.GenreRepository;

public class GenreServiceImp implements GenreService {
    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre createGenreIfNotExists(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre name cannot be empty");
        }

        Genre existing = repository.getGenreByName(name);
        if (existing != null) {
            return existing;
        }

        Genre genre = new Genre(name.trim());
        repository.createGenre(genre);
        return genre;
    }

    @Override
    public List<Genre> getAllGenres() {
        return repository.getGenres();
    }

    @Override
    public Genre findById(int id) {
        return repository.getGenreById(id);
    }

    @Override
    public Genre findByName(String name) {
        return repository.getGenreByName(name);
    }
}
