package com.library.model;

public class BookGenres {

    private int book_id;
    private int genre_id;

    //Constructores
    public BookGenres() {
    }

    public BookGenres(int book_id, int genre_id) {
        this.book_id = book_id;
        this.genre_id = genre_id;
    }

    //Getters & setters
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    //equals y HashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + book_id;
        result = prime * result + genre_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BookGenres other = (BookGenres) obj;
        if (book_id != other.book_id) {
            return false;
        }
        if (genre_id != other.genre_id) {
            return false;
        }
        return true;
    }
}
