package com.library;
//import com.library.config.DBManager;
import com.library.views.MainMenu;
import com.library.controller.BookController;
import com.library.service.BookService;
import com.library.service.BookServiceImpl;
import com.library.repository.BookRepository;
import com.library.repository.BookRepositoryImpl;
//import java.sql.SQLException;

public class App {
    public static void main(String [] args){
        BookRepository repository = new BookRepositoryImpl();
        BookService service = new BookServiceImpl(repository);
        BookController controller = new BookController(service);
        MainMenu menu = new MainMenu(controller);
        menu.show();
        /*try {
            DBManager.getConnection();
            System.out.println("conexion exitosa");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }  */

    }
}
