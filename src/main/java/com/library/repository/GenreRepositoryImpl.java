package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.library.config.DBManager;
import com.library.model.Genre;

public class GenreRepositoryImpl implements GenreRepository{

    public void createGenre(Genre genre){

        String sql = "INSERT INTO genres (name) VALUES (?)";
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

                st.setString(1, genre.getName());

                st.executeUpdate();
                try (ResultSet rs = st.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        genre.setId(id);
                      }}
            } catch (SQLException e){
                throw new RuntimeException("error to create new genre " + e.getMessage());
            }
    }

     public Genre getGenreById(int id){
         String sql = "SELECT id, name FROM genres WHERE id = ?";
         Genre genre = null;
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setInt(1,id);

                try(ResultSet rs = st.executeQuery()){
                    if(rs.next()){
                         genre = mapResultSetToGenre(rs);
                         }
                } 
                 
            } catch (SQLException e){
                throw new RuntimeException("error to get the genre by id" + e.getMessage());
            }
            return genre; 
    }

     public List<Genre> getGenreByName(String name){
        String sql = "SELECT id, name FROM genres WHERE name ILIKE ?";
         List<Genre> genres = new ArrayList<>();
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setString(1,"%"+ name +"%");

                try(ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        Genre genre = mapResultSetToGenre(rs);
                         genres.add(genre);
                         }
                } 
                  
            } catch (SQLException e){
                throw new RuntimeException("error to get the genre by name" + e.getMessage());
            }
            return genres;
    }

    public List<Genre> getGenres(){
        String sql = "SELECT id, name FROM genres ORDER BY name";
        List<Genre> genreList = new ArrayList<>();
        
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){
            try(ResultSet rs = st.executeQuery()){
                    while (rs.next()) {
                        Genre genre = mapResultSetToGenre(rs);
                         genreList.add(genre);
                         }
                } 
                  
            } catch (SQLException e){
                throw new RuntimeException("error to get the genre list" + e.getMessage());
            }
            return genreList; 
    }

     public void updateGenre(Genre genre){
        String sql= "UPDATE genres SET name = ? WHERE id = ?";
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setString(1,genre.getName());
                st.setInt(2, genre.getId());

                int rowsUpdated = st.executeUpdate();

                if (rowsUpdated == 0) {
                    System.out.println("The genre with id: " + genre.getId() +" is not found ");
                }
            } catch (SQLException e){
                throw new RuntimeException("error" + e.getMessage());
            }

     }

     public void deleteGenre(int id){
        String sql= "DELETE FROM genres WHERE id = ?";
        try( 
            Connection connection = DBManager.getConnection(); 
            PreparedStatement st = connection.prepareStatement(sql)){

                st.setInt(1,id);
                int rowsDeleted = st.executeUpdate();
        
                if (rowsDeleted > 0) {
                    System.out.println("The genre with ID " + id + " has been deleted succesfully.");
                }
            } catch (SQLException e){
                throw new RuntimeException("error in deleted action" + e.getMessage());
            } 

     }

     private Genre mapResultSetToGenre(ResultSet rs) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .build();
    }
}
