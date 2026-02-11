package com.library.service;

import java.util.List;

import com.library.model.Genre;

public interface GenreService {
    Genre createGenreIfNotExists(String name);
    List<Genre> getAllGenres();
    Genre findById(int id);
    Genre findByName(String name);
}

