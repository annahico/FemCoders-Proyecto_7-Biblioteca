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

public class BookRepositoryImpl implements BookRepository {

    @Override
    public void createBook(Book book) {

        String sql = "INSERT INTO books (title, isbn, description) VALUES (?,?,?);";
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setString(1, book.getTitle());
            st.setString(2, book.getIsbn());
            st.setString(3, book.getDescription());

            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    book.setId(id);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error in book creation" + e.getMessage());
        }
    }

    @Override
    public void saveBookAuthors(Book book) {
        String sqlAuthors = "INSERT INTO books_authors (book_id, author_id) VALUES (?, ?)";
        try (Connection connection = DBManager.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sqlAuthors)) {
                for (Author author : book.getAuthors()) {
                    st.setInt(1, book.getId());
                    st.setInt(2, author.getId());
                    st.addBatch();
                }
                st.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error to save relationships in book: " + e.getMessage());
        }
    }

    @Override
    public void saveBookGenres(Book book) {
        String sqlGenres = "INSERT INTO books_genres (book_id, genre_id) VALUES (?, ?)";
        try (Connection connection = DBManager.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sqlGenres)) {
                for (Genre genre : book.getGenres()) {
                    st.setInt(1, book.getId());
                    st.setInt(2, genre.getId());
                    st.addBatch();
                }
                st.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error to save relationships in book: " + e.getMessage());
        }
    }

    @Override
    public List<Book> getBookList() {
        String sql = """
                 SELECT
                    b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                    STRING_AGG(DISTINCT a.full_name, ', ') AS authors, 
                    STRING_AGG(DISTINCT g.name, ', ') AS genres
                    FROM books b
                    LEFT JOIN books_authors ba ON b.id = ba.book_id
                    LEFT JOIN authors a ON a.id = ba.author_id
                    LEFT JOIN books_genres bg ON b.id = bg.book_id
                    LEFT JOIN genres g ON g.id = bg.genre_id
                    GROUP BY b.id
                    ORDER BY b.id;
                 """;
        List<Book> list = new ArrayList<>();
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Book book = mapResultSetToBook(rs);
                    list.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains the book by id" + e.getMessage());
        }
        return list;
    }

    @Override
    public Book getBookbyId(int id) {
        String sql = """
                        SELECT b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                        STRING_AGG(DISTINCT a.full_name, ', ') AS authors, 
                        STRING_AGG(DISTINCT g.name, ', ') AS genres
                        FROM books b
                        LEFT JOIN books_authors ba ON b.id = ba.book_id
                        LEFT JOIN authors a ON a.id = ba.author_id
                        LEFT JOIN books_genres bg ON b.id = bg.book_id
                        LEFT JOIN genres g ON g.id = bg.genre_id
                        WHERE b.id = ?
                        GROUP BY b.id;
                         """;
        Book book = null;
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    book = mapResultSetToBook(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains the book by id" + e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> getBookbyTitle(String title) {
        String sql = """
                        SELECT b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                        STRING_AGG(DISTINCT a.full_name, ', ') AS authors, 
                        STRING_AGG(DISTINCT g.name, ', ') AS genres
                        FROM books b
                        LEFT JOIN books_authors ba ON b.id = ba.book_id
                        LEFT JOIN authors a ON a.id = ba.author_id
                        LEFT JOIN books_genres bg ON b.id = bg.book_id
                        LEFT JOIN genres g ON g.id = bg.genre_id
                        WHERE b.title ILIKE ?
                        GROUP BY b.id
                        ORDER BY b.title;
                         """;
        List<Book> books = new ArrayList<>();
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, "%" + title + "%");

            try (ResultSet rs = st.executeQuery()) {

                while (rs.next()) {
                    Book book = mapResultSetToBook(rs);
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains books by title" + e.getMessage());
        }
        return books;
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        String sql = """
                        SELECT b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                        STRING_AGG(DISTINCT a.full_name, ', ') AS authors, 
                        STRING_AGG(DISTINCT g.name, ', ') AS genres
                        FROM books b
                        LEFT JOIN books_authors ba ON b.id = ba.book_id
                        LEFT JOIN authors a ON a.id = ba.author_id
                        LEFT JOIN books_genres bg ON b.id = bg.book_id
                        LEFT JOIN genres g ON g.id = bg.genre_id
                        WHERE b.isbn = ?
                        GROUP BY b.id;
                         """;
        Book book = null;
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, isbn);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    book = mapResultSetToBook(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains the book by id" + e.getMessage());
        }
        return book;
    }

    @Override
    public List<Book> getBooksByAuthor(String name) {
        String sql = """
                SELECT
                b.id, b.title, b.isbn, b.description,
                STRING_AGG(DISTINCT a.full_name, ', ') AS authors,
                STRING_AGG(DISTINCT g.name, ', ') AS genres
                FROM books b
                JOIN books_authors ba ON b.id = ba.book_id
                JOIN authors a ON a.id = ba.author_id
                LEFT JOIN books_genres bg ON b.id = bg.book_id
                LEFT JOIN genres g ON g.id = bg.genre_id
                WHERE b.id IN (
                    SELECT ba2.book_id 
                    FROM books_authors ba2
                    JOIN authors a2 ON ba2.author_id = a2.id
                    WHERE a2.full_name ILIKE ?
                )
                GROUP BY b.id
                ORDER BY b.title;
                """;
        List<Book> booksByAuthor = new ArrayList<>();
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, "%" + name + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Book book = mapResultSetToBook(rs);
                    booksByAuthor.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains books by author" + e.getMessage());
        }
        return booksByAuthor;
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        String sql = """
                SELECT DISTINCT
                b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at,
                STRING_AGG(DISTINCT a.full_name, ', ') AS authors,
                STRING_AGG(DISTINCT g.name, ', ') AS genres
                FROM books b
                LEFT JOIN books_authors ba ON b.id = ba.book_id
                LEFT JOIN authors a ON a.id = ba.author_id
                JOIN books_genres bg ON b.id = bg.book_id
                JOIN genres g ON g.id = bg.genre_id
                WHERE b.id IN (
                    SELECT DISTINCT book_id
                    FROM books_genres
                    WHERE genre_id IN (
                        SELECT id
                        FROM genres
                        WHERE name ILIKE ? 
                    )
                )
                GROUP BY b.id
                ORDER BY b.title;
                """;
        List<Book> booksByGenre = new ArrayList<>();
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, "%" + genre + "%");

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Book book = mapResultSetToBook(rs);
                    booksByGenre.add(book);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("error obtains books by author" + e.getMessage());
        }
        return booksByGenre;
    }

    @Override
    public void updateBook(Book book) {
        String sql = """
                UPDATE books SET
                        title = ?,
                        isbn = ?,
                        description = ?,
                        updated_at = CURRENT_TIMESTAMP
                    WHERE id = ?;
                """;
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setString(1, book.getTitle());
            st.setString(2, book.getIsbn());
            st.setString(3, book.getDescription());
            st.setInt(4, book.getId());

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated == 0) {
                System.out.println("The book with id: " + book.getId() + " is not found ");
            }
        } catch (SQLException e) {
            throw new RuntimeException("error" + e.getMessage());
        }

    }

    @Override
    public void deleteBook(int id) {
        String sql = " DELETE FROM books WHERE id = ?;";
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, id);
            int rowsDeleted = st.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("The book with ID " + id + " has been deleted succesfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("error" + e.getMessage());
        }
    }

    @Override
    public void deleteBookAuthors(int bookId) {
        String sql = "DELETE FROM books_authors WHERE book_id = ?";
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, bookId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error" + e.getMessage());
        }
    }

    @Override
    public void deleteBookGenres(int bookId) {
        String sql = "DELETE FROM books_genres WHERE book_id = ?";
        try (
                Connection connection = DBManager.getConnection(); PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, bookId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("error" + e.getMessage());
        }
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
