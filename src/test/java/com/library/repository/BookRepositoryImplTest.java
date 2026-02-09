package com.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.library.config.DBManager;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Genre;

/**
 * Test completo con Mockito para BookRepositoryImpl
 * Incluye tests para todos los métodos y casos edge
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("BookRepositoryImpl Tests")
class BookRepositoryImplTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private BookRepositoryImpl bookRepository;

    private Book testBook;
    private Author testAuthor1;
    private Author testAuthor2;
    private Genre testGenre1;
    private Genre testGenre2;

    @BeforeEach
    void setUp() {
        // Crear autores de prueba
        testAuthor1 = Author.builder()
                .id(1)
                .fullName("Franz Kafka")
                .build();

        testAuthor2 = Author.builder()
                .id(2)
                .fullName("Gabriel García Márquez")
                .build();

        // Crear géneros de prueba
        testGenre1 = Genre.builder()
                .id(1)
                .name("Fantasy Literature")
                .build();

        testGenre2 = Genre.builder()
                .id(2)
                .name("Novella")
                .build();

        // Crear listas para el libro
        List<Author> authors = new ArrayList<>();
        authors.add(testAuthor1);
        
        List<Genre> genres = new ArrayList<>();
        genres.add(testGenre1);
        genres.add(testGenre2);

        // Crear libro de prueba con listas inicializadas
        testBook = Book.builder()
                .id(1)
                .title("La Metamorfosis")
                .isbn("978-0-000-00008-0")
                .description("A man wakes up one morning transformed into an insect")
                .authors(authors)
                .genres(genres)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ==================== TESTS PARA createBook ====================
    @Nested
    @DisplayName("createBook() Tests")
    class CreateBookTests {

        @Test
        @DisplayName("Debe crear un libro exitosamente y asignar el ID generado")
        void testCreateBook_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                        .thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(true);
                when(mockResultSet.getInt(1)).thenReturn(42);

                Book newBook = Book.builder()
                        .title("Nuevo Libro")
                        .isbn("978-1-234-56789-0")
                        .description("Descripción del nuevo libro")
                        .build();

                // Act
                bookRepository.createBook(newBook);

                // Assert
                assertEquals(42, newBook.getId(), "El ID debe ser asignado correctamente");
                
                verify(mockPreparedStatement).setString(1, "Nuevo Libro");
                verify(mockPreparedStatement).setString(2, "978-1-234-56789-0");
                verify(mockPreparedStatement).setString(3, "Descripción del nuevo libro");
                verify(mockPreparedStatement).executeUpdate();
                verify(mockPreparedStatement).getGeneratedKeys();
            }
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException cuando falla la creación")
        void testCreateBook_ThrowsException() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                        .thenThrow(new SQLException("Connection timeout"));

                Book newBook = Book.builder()
                        .title("Test Book")
                        .isbn("123456789")
                        .description("Test description")
                        .build();

                // Act & Assert
                RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                    bookRepository.createBook(newBook);
                });

                assertTrue(exception.getMessage().contains("error in book creation"));
                assertTrue(exception.getMessage().contains("Connection timeout"));
            }
        }

        @Test
        @DisplayName("Debe crear libro sin ID generado cuando ResultSet está vacío")
        void testCreateBook_NoGeneratedKey() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                        .thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false); // No hay clave generada

                Book newBook = Book.builder()
                        .title("Test Book")
                        .isbn("123456789")
                        .description("Test")
                        .build();

                // Act
                bookRepository.createBook(newBook);

                // Assert
                assertEquals(0, newBook.getId(), "El ID debe permanecer en 0");
                verify(mockPreparedStatement).executeUpdate();
            }
        }
    }

    // ==================== TESTS PARA getBookbyId ====================
    @Nested
    @DisplayName("getBookbyId() Tests")
    class GetBookByIdTests {

        @Test
        @DisplayName("Debe retornar un libro cuando existe")
        void testGetBookById_Found() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                when(mockResultSet.next()).thenReturn(true);
                mockBookResultSet();

                // Act
                Book result = bookRepository.getBookbyId(1);

                // Assert
                assertNotNull(result);
                assertEquals(1, result.getId());
                assertEquals("La Metamorfosis", result.getTitle());
                assertEquals("978-0-000-00008-0", result.getIsbn());
                assertEquals("A man wakes up...", result.getDescription());
                assertEquals(1, result.getAuthors().size());
                assertEquals("Franz Kafka", result.getAuthors().get(0).getFullName());
                assertEquals(2, result.getGenres().size());
                
                verify(mockPreparedStatement).setInt(1, 1);
            }
        }

        @Test
        @DisplayName("Debe retornar null cuando el libro no existe")
        void testGetBookById_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                Book result = bookRepository.getBookbyId(999);

                // Assert
                assertNull(result);
                verify(mockPreparedStatement).setInt(1, 999);
            }
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException en caso de error SQL")
        void testGetBookById_ThrowsException() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString()))
                        .thenThrow(new SQLException("Database error"));

                // Act & Assert
                RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                    bookRepository.getBookbyId(1);
                });

                assertTrue(exception.getMessage().contains("error obtains the book by id"));
            }
        }
    }

    // ==================== TESTS PARA getBookList ====================
    @Nested
    @DisplayName("getBookList() Tests")
    class GetBookListTests {

        @Test
        @DisplayName("Debe retornar lista de libros")
        void testGetBookList_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                // Simular 2 libros
                when(mockResultSet.next()).thenReturn(true, true, false);
                mockBookResultSet();

                // Act
                List<Book> results = bookRepository.getBookList();

                // Assert
                assertNotNull(results);
                assertEquals(2, results.size());
            }
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay libros")
        void testGetBookList_EmptyList() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                List<Book> results = bookRepository.getBookList();

                // Assert
                assertNotNull(results);
                assertTrue(results.isEmpty());
            }
        }
    }

    // ==================== TESTS PARA getBookbyTitle ====================
    @Nested
    @DisplayName("getBookbyTitle() Tests")
    class GetBookByTitleTests {

        @Test
        @DisplayName("Debe encontrar libros por título parcial")
        void testGetBookByTitle_Found() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                when(mockResultSet.next()).thenReturn(true, true, false);
                mockBookResultSet();

                // Act
                List<Book> results = bookRepository.getBookbyTitle("Meta");

                // Assert
                assertNotNull(results);
                assertEquals(2, results.size());
                verify(mockPreparedStatement).setString(1, "%Meta%");
            }
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no encuentra por título")
        void testGetBookByTitle_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                List<Book> results = bookRepository.getBookbyTitle("NoExiste");

                // Assert
                assertNotNull(results);
                assertTrue(results.isEmpty());
            }
        }
    }

    // ==================== TESTS PARA getBookByIsbn ====================
    @Nested
    @DisplayName("getBookByIsbn() Tests")
    class GetBookByIsbnTests {

        @Test
        @DisplayName("Debe encontrar libro por ISBN")
        void testGetBookByIsbn_Found() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                when(mockResultSet.next()).thenReturn(true);
                mockBookResultSet();

                // Act
                Book result = bookRepository.getBookByIsbn("978-0-000-00008-0");

                // Assert
                assertNotNull(result);
                assertEquals("978-0-000-00008-0", result.getIsbn());
                verify(mockPreparedStatement).setString(1, "978-0-000-00008-0");
            }
        }

        @Test
        @DisplayName("Debe retornar null cuando no encuentra por ISBN")
        void testGetBookByIsbn_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                Book result = bookRepository.getBookByIsbn("999-9-999-99999-9");

                // Assert
                assertNull(result);
            }
        }
    }

    // ==================== TESTS PARA getBooksByAuthor ====================
    @Nested
    @DisplayName("getBooksByAuthor() Tests")
    class GetBooksByAuthorTests {

        @Test
        @DisplayName("Debe encontrar libros por nombre de autor")
        void testGetBooksByAuthor_Found() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                when(mockResultSet.next()).thenReturn(true, false);
                mockBookResultSet();

                // Act
                List<Book> results = bookRepository.getBooksByAuthor("Kafka");

                // Assert
                assertNotNull(results);
                assertEquals(1, results.size());
                assertEquals("La Metamorfosis", results.get(0).getTitle());
                verify(mockPreparedStatement).setString(1, "%Kafka%");
            }
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay libros del autor")
        void testGetBooksByAuthor_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                List<Book> results = bookRepository.getBooksByAuthor("AutorInexistente");

                // Assert
                assertNotNull(results);
                assertTrue(results.isEmpty());
            }
        }
    }

    // ==================== TESTS PARA getBooksByGenre ====================
    @Nested
    @DisplayName("getBooksByGenre() Tests")
    class GetBooksByGenreTests {

        @Test
        @DisplayName("Debe encontrar libros por género")
        void testGetBooksByGenre_Found() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                
                when(mockResultSet.next()).thenReturn(true, false);
                mockBookResultSet();

                // Act
                List<Book> results = bookRepository.getBooksByGenre("Fantasy");

                // Assert
                assertNotNull(results);
                assertEquals(1, results.size());
                verify(mockPreparedStatement).setString(1, "%Fantasy%");
            }
        }

        @Test
        @DisplayName("Debe retornar lista vacía cuando no hay libros del género")
        void testGetBooksByGenre_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
                when(mockResultSet.next()).thenReturn(false);

                // Act
                List<Book> results = bookRepository.getBooksByGenre("GeneroInexistente");

                // Assert
                assertNotNull(results);
                assertTrue(results.isEmpty());
            }
        }
    }

    // ==================== TESTS PARA updateBook ====================
    @Nested
    @DisplayName("updateBook() Tests")
    class UpdateBookTests {

        @Test
        @DisplayName("Debe actualizar libro exitosamente")
        void testUpdateBook_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1);

                Book bookToUpdate = Book.builder()
                        .id(1)
                        .title("Título Actualizado")
                        .isbn("978-0-000-11111-1")
                        .description("Nueva descripción")
                        .build();

                // Act
                bookRepository.updateBook(bookToUpdate);

                // Assert
                verify(mockPreparedStatement).setString(1, "Título Actualizado");
                verify(mockPreparedStatement).setString(2, "978-0-000-11111-1");
                verify(mockPreparedStatement).setString(3, "Nueva descripción");
                verify(mockPreparedStatement).setInt(4, 1);
                verify(mockPreparedStatement).executeUpdate();
            }
        }

        @Test
        @DisplayName("No debe lanzar excepción cuando no encuentra el libro")
        void testUpdateBook_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(0); // No rows updated

                Book bookToUpdate = Book.builder()
                        .id(999)
                        .title("Test")
                        .isbn("123")
                        .description("Test")
                        .build();

                // Act & Assert
                assertDoesNotThrow(() -> bookRepository.updateBook(bookToUpdate));
            }
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException en caso de error SQL")
        void testUpdateBook_ThrowsException() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString()))
                        .thenThrow(new SQLException("Update failed"));

                Book bookToUpdate = Book.builder()
                        .id(1)
                        .title("Test")
                        .isbn("123")
                        .description("Test")
                        .build();

                // Act & Assert
                assertThrows(RuntimeException.class, () -> {
                    bookRepository.updateBook(bookToUpdate);
                });
            }
        }
    }

    // ==================== TESTS PARA deleteBook ====================
    @Nested
    @DisplayName("deleteBook() Tests")
    class DeleteBookTests {

        @Test
        @DisplayName("Debe eliminar libro exitosamente")
        void testDeleteBook_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(1);

                // Act
                bookRepository.deleteBook(1);

                // Assert
                verify(mockPreparedStatement).setInt(1, 1);
                verify(mockPreparedStatement).executeUpdate();
            }
        }

        @Test
        @DisplayName("No debe lanzar excepción cuando no encuentra el libro")
        void testDeleteBook_NotFound() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(0);

                // Act & Assert
                assertDoesNotThrow(() -> bookRepository.deleteBook(999));
            }
        }
    }

    // ==================== TESTS PARA saveBookAuthors ====================
    @Nested
    @DisplayName("saveBookAuthors() Tests")
    class SaveBookAuthorsTests {

        @Test
        @DisplayName("Debe guardar autores del libro exitosamente")
        void testSaveBookAuthors_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeBatch()).thenReturn(new int[]{1});

                // Act
                bookRepository.saveBookAuthors(testBook);

                // Assert
                verify(mockPreparedStatement).setInt(1, testBook.getId());
                verify(mockPreparedStatement).setInt(2, testAuthor1.getId());
                verify(mockPreparedStatement).addBatch();
                verify(mockPreparedStatement).executeBatch();
            }
        }

        @Test
        @DisplayName("Debe guardar múltiples autores")
        void testSaveBookAuthors_MultipleAuthors() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                List<Author> multipleAuthors = new ArrayList<>();
                multipleAuthors.add(testAuthor1);
                multipleAuthors.add(testAuthor2);
                
                Book bookWithMultipleAuthors = Book.builder()
                        .id(1)
                        .title("Test Book")
                        .isbn("123456")
                        .description("Test")
                        .authors(multipleAuthors)
                        .genres(new ArrayList<>())
                        .build();
                
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeBatch()).thenReturn(new int[]{1, 1});

                // Act
                bookRepository.saveBookAuthors(bookWithMultipleAuthors);

                // Assert
                verify(mockPreparedStatement, times(2)).addBatch();
                verify(mockPreparedStatement).executeBatch();
            }
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException en caso de error")
        void testSaveBookAuthors_ThrowsException() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString()))
                        .thenThrow(new SQLException("Constraint violation"));

                // Act & Assert
                RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                    bookRepository.saveBookAuthors(testBook);
                });

                assertTrue(exception.getMessage().contains("Error to save relationships in book"));
            }
        }
    }

    // ==================== TESTS PARA saveBookGenres ====================
    @Nested
    @DisplayName("saveBookGenres() Tests")
    class SaveBookGenresTests {

        @Test
        @DisplayName("Debe guardar géneros del libro exitosamente")
        void testSaveBookGenres_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeBatch()).thenReturn(new int[]{1, 1});

                // Act
                bookRepository.saveBookGenres(testBook);

                // Assert
                verify(mockPreparedStatement, times(2)).addBatch();
                verify(mockPreparedStatement).executeBatch();
            }
        }

        @Test
        @DisplayName("Debe lanzar RuntimeException en caso de error")
        void testSaveBookGenres_ThrowsException() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString()))
                        .thenThrow(new SQLException("Database error"));

                // Act & Assert
                RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                    bookRepository.saveBookGenres(testBook);
                });

                assertTrue(exception.getMessage().contains("Error to save relationships in book"));
            }
        }
    }

    // ==================== TESTS PARA deleteBookAuthors ====================
    @Nested
    @DisplayName("deleteBookAuthors() Tests")
    class DeleteBookAuthorsTests {

        @Test
        @DisplayName("Debe eliminar autores del libro exitosamente")
        void testDeleteBookAuthors_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(2);

                // Act
                bookRepository.deleteBookAuthors(1);

                // Assert
                verify(mockPreparedStatement).setInt(1, 1);
                verify(mockPreparedStatement).executeUpdate();
            }
        }
    }

    // ==================== TESTS PARA deleteBookGenres ====================
    @Nested
    @DisplayName("deleteBookGenres() Tests")
    class DeleteBookGenresTests {

        @Test
        @DisplayName("Debe eliminar géneros del libro exitosamente")
        void testDeleteBookGenres_Success() throws SQLException {
            try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
                // Arrange
                dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
                
                when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
                when(mockPreparedStatement.executeUpdate()).thenReturn(2);

                // Act
                bookRepository.deleteBookGenres(1);

                // Assert
                verify(mockPreparedStatement).setInt(1, 1);
                verify(mockPreparedStatement).executeUpdate();
            }
        }
    }

    // ==================== MÉTODOS AUXILIARES ====================
    /**
     * Método auxiliar para mockear el ResultSet con datos de prueba
     */
    private void mockBookResultSet() throws SQLException {
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("title")).thenReturn("La Metamorfosis");
        when(mockResultSet.getString("isbn")).thenReturn("978-0-000-00008-0");
        when(mockResultSet.getString("description")).thenReturn("A man wakes up...");
        when(mockResultSet.getString("authors")).thenReturn("Franz Kafka");
        when(mockResultSet.getString("genres")).thenReturn("Fantasy Literature, Novella");
        when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
        when(mockResultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
    }
}
