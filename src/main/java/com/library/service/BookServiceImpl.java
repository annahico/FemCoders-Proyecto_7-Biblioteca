package com.library.service;
import java.util.List;
import java.util.regex.Pattern;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;
//import com.library.service.BookService;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9-]{10,17}$");

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public void createBook(Book book){
        //existe el autor? si no, lo crea (y si es que sí, recupera el id)
        //existe el genero? sino, lo crea (y si es que sí, recupera el id)
        ///existe el libro?
        // crea libro
        //CREA ENLACE A TABLAS SECUNDARIAS

        if (bookRepository.getBookByIsbn(book.getIsbn()) != null) {
            throw new IllegalArgumentException("A book with this ISBN already exists.");
        }
        validateBook(book);
        bookRepository.createBook(book);
        //bookRepository.saveBookAuthors(book);
        //bookRepository.saveBookGenres(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getBookList();
    }

    @Override
    public Book findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID.");
        }
        return bookRepository.getBookbyId(id);
    }

    @Override
    public List<Book> findByTitle(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        return bookRepository.getBookbyTitle(title.trim());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        if (author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be empty.");
        }
        return bookRepository.getBooksByAuthor(author.trim());
    }

    @Override
    public List<Book> findByGenre(String genre) {
        if (genre.isBlank()) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        return bookRepository.getBooksByGenre(genre.trim());
    }

    @Override
    public void updateBook(Book book) {
        if (book == null || book.getId() <= 0) {
            throw new IllegalArgumentException("Invalid book data.");
        }
        validateBook(book);
    
        bookRepository.updateBook(book);
        bookRepository.deleteBookAuthors(book.getId());
        bookRepository.deleteBookGenres(book.getId());
        bookRepository.saveBookAuthors(book);
        bookRepository.saveBookGenres(book);
    }

    @Override
    public void deleteBook(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid book ID.");
        }

        bookRepository.deleteBookAuthors(id);
        bookRepository.deleteBookGenres(id);
        bookRepository.deleteBook(id);
    }

    private void validateBook(Book book){
        if (book == null){
            throw new IllegalArgumentException("Enter a book, cannot be null");
        }
        if (book.getTitle() == null || book.getTitle().isBlank()){
            throw new IllegalArgumentException("Enter a title (required)");
        }

        if (!ISBN_PATTERN.matcher(book.getIsbn()).matches()) {
            throw new IllegalArgumentException("Invalid ISBN format.");
        }

        if (book.getDescription() != null && book.getDescription().length() > 200){
            throw new IllegalArgumentException("Enter a description (200 characters max)");
        }
        if (book.getAuthors() == null || book.getAuthors().isEmpty()){
            throw new IllegalArgumentException("It must have at least one author");
        }
        if (book.getGenres() == null || book.getGenres().isEmpty()){
            throw new IllegalArgumentException("You must have at least one gender");
        }
    }
    
}

