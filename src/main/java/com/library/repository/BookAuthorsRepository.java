package com.library.repository;

import com.library.model.BookAuthors;

public interface BookAuthorsRepository {

    public void createBookAuthors(int book_id, int author_id);

    public BookAuthors getBookAuthorsByBook(int book_id);

    public BookAuthors getBookAuthorsByAuthor(int author_id);

    public void updateBookAuthors(BookAuthors rel);

    public void deleteBookAuthors(BookAuthors rel);
}
//esta interface no me convence, q conste