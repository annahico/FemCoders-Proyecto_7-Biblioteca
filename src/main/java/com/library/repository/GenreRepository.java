package com.library.repository;

import java.util.List;

import com.library.model.Genre;

public interface GenreRepository {

     Genre createGenre(Genre genre);

     Genre getGenreById(int id);

     List<Genre> getGenreByName(String name);

     Genre getGenreByNameStrict (String  name);

     List<Genre> getGenres();

     void updateGenre(Genre genre);

     void deleteGenre(int id);

}
