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
import com.library.model.Book;
import com.library.model.Genre;

public class AuthorRepositoryImpl implements AuthorRepository {

    @Override
    public void createAuthor(Author author) {
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
        String sql = "SELECT id, full_name FROM authors WHERE full_name = ?";
        Author author = null;

        try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, "%" + name + "%");

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

    // @Override
    // public List<Book> getBooksbyAuthor(Author author) {
    //     String sql = """
    //             SELECT
    //             b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
    //             STRING_AGG(DISTINCT a.full_name, ', ') AS authors,
    //             STRING_AGG(DISTINCT g.name, ', ') AS genres
    //             FROM books b
    //             JOIN books_authors ba ON b.id = ba.book_id
    //             JOIN authors a ON a.id = ba.author_id
    //             LEFT JOIN books_genres bg ON b.id = bg.book_id
    //             LEFT JOIN genres g ON g.id = bg.genre_id
    //             WHERE b.id IN (
    //                 SELECT ba2.book_id
    //                 FROM books_authors ba2
    //                 WHERE ba2.author_id = ?
    //             )
    //             GROUP BY b.id
    //             ORDER BY b.title;
    //             """;

    //     List<Book> books = new ArrayList<>();

    //     try (Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

    //         st.setInt(1, author.getId());

    //         try (ResultSet rs = st.executeQuery()) {
    //             while (rs.next()) {
    //                 Book book = mapResultSetToBook(rs);
    //                 books.add(book);
    //             }
    //         }
    //     } catch (SQLException e) {
    //         throw new RuntimeException("Error getting books by author: " + e.getMessage());
    //     }
    //     return books;
    // }

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

    // ========== HELPER METHODS ==========
    private Author mapResultSetToAuthor(ResultSet rs) throws SQLException {
        return Author.builder()
                .id(rs.getInt("id"))
                .fullName(rs.getString("full_name"))
                .build();
    }

    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        String genreString = rs.getString("genres");
        String authorString = rs.getString("authors");

        Book book = Book.builder()
                .id(rs.getInt("id"))
                .title(rs.getString("title"))
                .isbn(rs.getString("isbn"))
                .description(rs.getString("description"))
                .createdAt(rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime() : null)
                .updatedAt(rs.getTimestamp("updated_at") != null ? rs.getTimestamp("updated_at").toLocalDateTime() : null)
                .build();

        if (genreString != null && !genreString.isEmpty()) {
            for (String name : genreString.split(", ")) {
                book.getGenres().add(Genre.builder().name(name).build());
            }
        }

        if (authorString != null && !authorString.isEmpty()) {
            for (String name : authorString.split(", ")) {
                book.getAuthors().add(Author.builder().fullName(name).build());
            }
        }
        return book;
    }
}
