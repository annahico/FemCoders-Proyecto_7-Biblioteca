package com.library.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.library.config.DBManager;
import com.library.model.Author;

public class AuthorRepositoryImpl implements AuthorRepository {

    @Override
    public Author createAuthor(Author author) {
        String sql = "INSERT INTO authors (full_name) VALUES (?)";

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, author.getFullName());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    author.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating author: " + e.getMessage());
        }
        return author;
    }

    @Override
    public Author getAuthorById(int id) {
        String sql = "SELECT id, full_name FROM authors WHERE id = ?";
        Author author = null;

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    author = mapResultSetToAuthor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting author by ID: " + e.getMessage());
        }
        return author;
    }

    @Override
    public List<Author> getAuthorByName(String name) {
        String sql = "SELECT id, full_name FROM authors WHERE full_name ILIKE ?";
        List<Author> authorList = new ArrayList<>();

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, "%" + name + "%");

            try (ResultSet rs = st.executeQuery()) {
                 while (rs.next()) {
                    Author author = mapResultSetToAuthor(rs);
                    authorList.add(author);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting author by name: " + e.getMessage());
        }
        return authorList;
    }

    @Override
    public Author getAuthorByNameStrict(String name) {
        String sql = "SELECT id, full_name FROM authors WHERE LOWER(full_name) = LOWER(?)";
        Author author = null;

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, name);

            try (ResultSet rs = st.executeQuery()) {
                 while (rs.next()) {
                    author = mapResultSetToAuthor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting author by name: " + e.getMessage());
        }
        return author;
    }

    @Override
    public void updateAuthor(Author author) {
        String sql = "UPDATE authors SET full_name = ? WHERE id = ?";

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, author.getFullName());
            st.setInt(2, author.getId());

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("The author with ID " + author.getId() + " was not found.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating author: " + e.getMessage());
        }
    }

    @Override
    public void deleteAuthor(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            int rowsDeleted = st.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("The author with ID " + id + " has been deleted successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting author: " + e.getMessage());
        }
    }

    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("full_name"))
                .build();
    }
}