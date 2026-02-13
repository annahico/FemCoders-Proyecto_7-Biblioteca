package com.library.service;
import java.util.List;
import com.library.model.Genre;
import com.library.repository.GenreRepository;

public class GenreServiceImpl implements GenreService {
    private final GenreRepository repository;

    public GenreServiceImpl(GenreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Genre createGenreIfNotExists(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre name cannot be empty");
        }

        Genre existing = repository.getGenreByNameStrict(name);
        if (existing != null) {
            return existing;
        }else{
        Genre genre =  Genre.builder()
                        .name(name)
                        .build();
        Genre newGenre = repository.createGenre(genre);
        return newGenre;
        }
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
    public List<Genre> findByName(String name) {
        return repository.getGenreByName(name);
    }
}