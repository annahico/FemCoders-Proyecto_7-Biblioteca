package com.library.repository;

import com.library.model.Book;
import com.library.model.Genre;

public interface GenreRepository {

 public void createGenre(Genre genre);

    public Genre getGenreById(int id);

    public Genre getGenreByName(String name);

    public Book getBookbyGenre(Genre genre); //mismo q en author, debería devolver un array

    public void updateGenre(Genre genre);

    public void deleteGenre(int id);

}
