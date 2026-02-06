package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;

public class BookServiceImpl implements BookService {
    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository){
        this.repository = repository;
    }

    @Override
    public void createBook(Book book){
        validateBook(book);
        repository.save(book);//consultar si falta generar
    }

    private void validateBook(Book book){
        if (book == null){
            throw new IllegalArgumentException("Enter a book, cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()){
            throw new IllegalArgumentException("Enter a title (required)");
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()){
            throw new IllegalArgumentException("Enter a isbn (required)");
        }
        if (book.getDescription() != null || book.getDescription().length() > 200){
            throw new IllegalArgumentException("Enter a description (200 characters max)");
        }
        if (book.getAuthors() == null || book.getAuthors().isEmpty()){
            throw new IllegalArgumentException("It must have at least one author");
        }
        if (book.getGender() == null || book.getGender().isEmpty()){
            throw new IllegalArgumentException("You must have at least one gender");
        }
    }
}

