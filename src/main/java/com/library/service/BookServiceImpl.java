package com.library.service;

import java.util.List;
import java.util.regex.Pattern;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Genre;
import com.library.repository.BookRepository;
//import com.library.service.AuthorService;
//import com.library.service.GenreService;
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    private static final Pattern ISBN_PATTERN = Pattern.compile("^[0-9-]{10,17}$");

    public BookServiceImpl(BookRepository bookRepository,
                            AuthorService authorService,
                            GenreService genreService){
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public void createBook(Book book){
        validateBook(book);

        if (bookRepository.getBookByIsbn(book.getIsbn()) != null) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        bookRepository.createBook(book);

        for (Author author : book.getAuthors()) {
            authorService.createAuthorIfNotExists(author.getFullName()); 
        }
          bookRepository.saveBookAuthors(book);

        for (Genre genre : book.getGenres()) {
             genreService.createGenreIfNotExists(genre.getName());
        
        }
         bookRepository.saveBookGenres(book);
    }

    @Override
    public void updateBook(Book book) {

        validateBook(book);

        Book existing = bookRepository.getBookbyId(book.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Book not found");
        }

        bookRepository.updateBook(book);
        bookRepository.deleteBookAuthors(book.getId());
        bookRepository.deleteBookGenres(book.getId());

        for (Author author : book.getAuthors()) {
            authorService.createAuthorIfNotExists(author.getFullName());
            bookRepository.saveBookAuthors(book);
        }

        for (Genre genre : book.getGenres()) {
              genreService.createGenreIfNotExists(genre.getName());
            bookRepository.saveBookGenres(book);
        }    
    }

    @Override
    public void deleteBook(int id) {

        Book book = bookRepository.getBookbyId(id);
        if (book == null) {
            throw new IllegalArgumentException("Book not found with ID: " + id);
        }

        bookRepository.deleteBookAuthors(id);
        bookRepository.deleteBookGenres(id);
        bookRepository.deleteBook(id);
    }

    @Override
    public Book findById(int id) {
        return bookRepository.getBookbyId(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getBookList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookRepository.getBookbyTitle(title);
    }
    
    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.getBooksByAuthor(author);
    }
    
    @Override
    public List<Book> findByGenre(String genre) {
        return bookRepository.getBooksByGenre(genre);
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

