package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.library.config.DBManager;
import com.library.model.Book;

public class BookRepositoryImpl implements BookRepository{

    @Override
    public void createBook(Book book){

        String sql = "INSERT INTO books (title, isbn, description) VALUES (?,?,?);";
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                st.setString(1,book.getTitle());
                st.setString(2,book.getIsbn());
                st.setString(3, book.getDescription());

                st.executeUpdate();

                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        book.setId(id);
                      }}
            } catch (SQLException e){
                throw new RuntimeException("error" + e.getMessage());
            }
    }
    @Override
    public Book getBookbyId (int id){
        String sql = """
                        SELECT b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                        a.full_name AS author, 
                        STRING_AGG(DISTINCT g.name, ', ') AS genres
                        FROM books b
                        LEFT JOIN books_authors ba ON b.id = ba.book_id
                        LEFT JOIN authors a ON a.id = ba.author_id
                        LEFT JOIN books_genres bg ON b.id = bg.book_id
                        LEFT JOIN genres g ON g.id = bg.genre_id
                        WHERE b.id = ?
                        GROUP BY b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at, a.full_name;
                         """;
        Book book = null;

        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setInt(1,id);

                st.executeUpdate();
            } catch (SQLException e){
                throw new RuntimeException("error" + e.getMessage());
            }
    }



    }
    @Override
    public Book getBookbyTitle(String title){

    }
    @Override
    public Book getBookByIsbn(String isbn){

    }
    @Override
    public void updateBook(Book book){

    }
    @Override
    public void deleteBook(int id){

    }

}
/* estructura par la lamada: 
public void createBook(Book book){

        String sql = "";
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setString(,dof...)

                st.executeUpdate();
            } catch (SQLException e){
                throw new RuntimeException("error" + e.getMessage());
            }
    }
 */