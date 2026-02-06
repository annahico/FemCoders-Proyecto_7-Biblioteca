package com.library.repository;

import com.library.model.BookGenres;

public interface BookGenresRepository {

    
    public void createBookGenres(int book_id, int genre_id);

    public BookGenres getBookGenresByBook(int book_id);

    public BookGenres getBookGenresByGenre(int genre_id);

    public void updateBookGenres(BookGenres rel);

    public void deleteBookGenres(BookGenres rel);
}
//esta interface no me convence, q conste

