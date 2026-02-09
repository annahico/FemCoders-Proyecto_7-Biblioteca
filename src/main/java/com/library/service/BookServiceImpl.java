package com.library.service;
import java.util.List;
import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;
import com.library.service.BookService;

public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository = new BookRepositoryImpl();

    @Override
    public void createBook(Book book){
        //existe el autor? si no, lo crea (y si es que sí, recupera el id)
        //existe el genero? sino, lo crea (y si es que sí, recupera el id)
        ///existe el libro?
        // crea libro
        //CREA ENLACE A TABLAS SECUNDARIAS


        
        bookRepository.createBook(book);
        bookRepository.saveBookAuthors(book);
        bookRepository.saveBookGenres(book);
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
        if (book.getGenres() == null || book.getGenres().isEmpty()){
            throw new IllegalArgumentException("You must have at least one gender");
        }
    }
    
}
/* 
    @Override
    public void updateBook(Book book){

        //valorar que el libro exista
        //valorar que el autor exista
        //valorar que el género exista
        //si cambia autor o género, se tiene que actualizar lastablas intermedias, eliminando y creando de nuevo la relacion
            //deleteBookGenres
            //saveBookGenres
        validateBook(book);
        if(!bookRepository.existsByIsbn(book.getIsbn())){
            throw new IllegalArgumentException("There is no book with that ISBN");     
        }
        bookRepository.update(book);
    }

    @Override
    public List<Book> getAllBooks(){
        return bookRepository.searchAllBooks();
    }

    @Override
    public List<Book> searchByTitle(String title){
        if (title == null || title.isBlank()){
            throw new IllegalArgumentException("A title is required");
        }
        return bookRepository.searchByTitle(title);
    }

    @Override
    public List<Book> searchByAuthor(String author){
        if (author == null || author.isBlank()){
            throw new IllegalArgumentException("A author is required");
        }
        return bookRepository.searchByAuthor(author);
    }

    @Override
    public List<Book> searchByGenre(String genre){
        if (genre == null || genre.isBlank()){
            throw new IllegalArgumentException("A author is required");
        }
        return bookRepository.searchByGenre(genre);
    }

    @Override
    public void deleteById(int id) {
        if (id == null || id.isBlank()) {
        throw new IllegalArgumentException("Invalid Id");
        }

        if (!bookRepository.existsByIsbn(id)) {
            throw new IllegalArgumentException("There is no book with that Id");
        }
        bookRepository.deleteById(id);
    }
}*/

