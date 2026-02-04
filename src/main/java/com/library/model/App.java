package com.library.model;
import com.library.config.DBManager;
import java.sql.SQLException;

public class App {
public static void main(String [] args){

    try {
        DBManager.getConnection();
        System.out.println("conexion exitosa");
    } catch (SQLException e) {
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
}
}
