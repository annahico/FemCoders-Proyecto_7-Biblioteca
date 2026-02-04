package com.library.model;

public class BookAuthors {

    private int book_id;
    private int author_id;
    
    //Constructores
    public BookAuthors() {
    }

    public BookAuthors(int book_id, int author_id) {
        this.book_id = book_id;
        this.author_id = author_id;
    }

    //Getters & setters
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    //equals y HashCode
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + book_id;
        result = prime * result + author_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookAuthors other = (BookAuthors) obj;
        if (book_id != other.book_id)
            return false;
        if (author_id != other.author_id)
            return false;
        return true;
    }
}
 
    
    


    