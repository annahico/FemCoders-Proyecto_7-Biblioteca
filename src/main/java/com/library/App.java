package com.library;
import com.library.views.MainMenu;
import com.library.controller.BookController;
import com.library.service.BookService;
import com.library.service.BookServiceImpl;
import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;

public class App {
    public static void main(String [] args){
        BookRepository repository = new BookRepositoryImpl();
        BookService service = new BookServiceImpl(repository);
        BookController controller = new BookController(service);
        MainMenu menu = new MainMenu(controller);
        menu.show();
    }
}
