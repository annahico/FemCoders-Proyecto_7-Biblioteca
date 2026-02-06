package com.library.service;
import java.util.List;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.service.BookService;

public class BookServiceImpl implements BookService {
    private final BookRepositoryImpl repository;

    public BookServiceImpl(BookRepositoryImpl repository){
        this.repository = repository;
    }

    @Override
    public void createBook(Book book){
        validateBook(book);
        repository.createBook(book);
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

    @Override
    public void updateBook(Book book){
        validateBook(book);
        if(!repository.existsByIsbn(book.getIsbn())){
            throw new IllegalArgumentException("There is no book with that ISBN");     
        }
        repository.update(book);
    }

    @Override
    public List<Book> getAllBooks(){
        return repository.searchAllBooks();
    }

    @Override
    public List<Book> searchByTitle(String title){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("A title is required");
        }
        return repository.searchByTitle(title);
    }

    @Override
    public List<Book> searchByAuthor(String author){
        if (author == null || author.isBlank()){
            throw new IllegalArgumentException("A author is required");
        }
        return repository.searchByAuthor(author);
    }

    @Override
    public List<Book> searchByGenre(String genre){
        if (genre == null || genre.isBlank()){
            throw new IllegalArgumentException("A author is required");
        }
        return repository.searchByGenre(genre);
    }

    @Override
    public void deleteById(int id) {
        if (id == null || id.isBlank()) {
        throw new IllegalArgumentException("Invalid Id");
        }

        if (!repository.existsByIsbn(id)) {
            throw new IllegalArgumentException("There is no book with that Id");
        }
        repository.deleteById(id);
    }
}

