package com.library;
import com.library.views.MainMenu;
import com.library.controller.AuthorController;
import com.library.controller.BookController;
import com.library.controller.GenreController;
import com.library.service.AuthorService;
import com.library.service.AuthorServiceImpl;
import com.library.service.BookService;
import com.library.service.BookServiceImpl;
import com.library.service.GenreService;
import com.library.service.GenreServiceImpl;
import com.library.repository.AuthorRepository;
import com.library.repository.AuthorRepositoryImpl;
import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;
import com.library.repository.GenreRepository;
import com.library.repository.GenreRepositoryImpl;

public class App {
    public static void main(String [] args){
        BookRepository bookRepository = new BookRepositoryImpl();
        AuthorRepository authorRepository = new AuthorRepositoryImpl();
        GenreRepository genreRepository = new GenreRepositoryImpl();

        AuthorService authorService = new AuthorServiceImpl(authorRepository);
        GenreService genreService = new GenreServiceImpl(genreRepository);
        BookService service = new BookServiceImpl(bookRepository, authorService, genreService);
        
        BookController bController = new BookController(service);
        GenreController gController = new GenreController(genreService);
        AuthorController aController = new AuthorController(authorService);
        MainMenu menu = new MainMenu(bController);
        menu.show();
    }
}