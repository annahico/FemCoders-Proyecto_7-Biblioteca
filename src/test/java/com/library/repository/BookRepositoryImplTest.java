package com.library.repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.library.config.DBManager;
import com.library.model.Author;
import com.library.model.Book;
import com.library.model.Genre;

@ExtendWith(MockitoExtension.class)
class BookRepositoryImplTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private BookRepositoryImpl repository;
    private Book libroTest;
    private Author autorTest;
    private Genre generoTest;

    @BeforeEach
    void configurarTest() {
        repository = new BookRepositoryImpl();
        
        autorTest = Author.builder()
                .id(1)
                .fullName("Franz Kafka")
                .build();

        generoTest = Genre.builder()
                .id(1)
                .name("Novella")
                .build();

        libroTest = Book.builder()
                .id(1)
                .title("La Metamorfosis")
                .isbn("978-0-000-00008-0")
                .description("Un hombre se despierta transformado en insecto")
                .build();
        
        libroTest.getAuthors().add(autorTest);
        libroTest.getGenres().add(generoTest);
    }

    @Test
    void debeCrearUnLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
     
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                    .thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt(1)).thenReturn(10);

            Book nuevoLibro = Book.builder()
                    .title("Nuevo Libro")
                    .isbn("978-1-234-56789-0")
                    .description("Descripción del libro")
                    .build();

            repository.createBook(nuevoLibro);

            assertEquals(10, nuevoLibro.getId());
        }
    }

    @Test
    void debeBuscarLibroPorId() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {

            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            
            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("title")).thenReturn("La Metamorfosis");
            when(mockResultSet.getString("isbn")).thenReturn("978-0-000-00008-0");
            when(mockResultSet.getString("description")).thenReturn("Descripción");
            when(mockResultSet.getString("authors")).thenReturn("Franz Kafka");
            when(mockResultSet.getString("genres")).thenReturn("Novella");
            when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            Book resultado = repository.getBookbyId(1);

            assertNotNull(resultado);
            assertEquals(1, resultado.getId());
            assertEquals("La Metamorfosis", resultado.getTitle());
        }
    }

    @Test
    void debeRetornarNullSiNoEncuentraLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {

            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            when(mockResultSet.next()).thenReturn(false); // No encuentra nada

            Book resultado = repository.getBookbyId(999);

            assertNull(resultado);
        }
    }

    @Test
    void debeListarTodosLosLibros() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
  
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true, true, false);
            when(mockResultSet.getInt("id")).thenReturn(1, 2);
            when(mockResultSet.getString("title")).thenReturn("Libro 1", "Libro 2");
            when(mockResultSet.getString("isbn")).thenReturn("111", "222");
            when(mockResultSet.getString("description")).thenReturn("Desc 1", "Desc 2");
            when(mockResultSet.getString("authors")).thenReturn("Autor 1", "Autor 2");
            when(mockResultSet.getString("genres")).thenReturn("Genero 1", "Genero 2");
            when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            List<Book> resultados = repository.getBookList();

            assertEquals(2, resultados.size());
        }
    }


    @Test
    void debeBuscarLibrosPorTitulo() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
       
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            
            when(mockResultSet.next()).thenReturn(true, false);
            when(mockResultSet.getInt("id")).thenReturn(1);
            when(mockResultSet.getString("title")).thenReturn("La Metamorfosis");
            when(mockResultSet.getString("isbn")).thenReturn("978-0-000-00008-0");
            when(mockResultSet.getString("description")).thenReturn("Desc");
            when(mockResultSet.getString("authors")).thenReturn("Franz Kafka");
            when(mockResultSet.getString("genres")).thenReturn("Novella");
            when(mockResultSet.getTimestamp("created_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));
            when(mockResultSet.getTimestamp("updated_at")).thenReturn(Timestamp.valueOf(LocalDateTime.now()));

            List<Book> resultados = repository.getBookbyTitle("Meta");

            assertEquals(1, resultados.size());
            verify(mockPreparedStatement).setString(1, "%Meta%");
        }
    }

    @Test
    void debeActualizarUnLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
       
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            Book libroActualizado = Book.builder()
                    .id(1)
                    .title("Título Nuevo")
                    .isbn("978-1111111111")
                    .description("Nueva descripción")
                    .build();

            repository.updateBook(libroActualizado);

            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void debeEliminarUnLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
           
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            repository.deleteBook(1);

            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void debeGuardarAutoresDelLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
          
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeBatch()).thenReturn(new int[]{1});

            repository.saveBookAuthors(libroTest);

            verify(mockPreparedStatement).addBatch();
            verify(mockPreparedStatement).executeBatch();
        }
    }

    @Test
    void debeGuardarGenerosDelLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
         
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeBatch()).thenReturn(new int[]{1});

            repository.saveBookGenres(libroTest);

            verify(mockPreparedStatement).addBatch();
            verify(mockPreparedStatement).executeBatch();
        }
    }

    @Test
    void debeEliminarAutoresDelLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
            
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            repository.deleteBookAuthors(1);

            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    void debeEliminarGenerosDelLibro() throws SQLException {
        try (MockedStatic<DBManager> dbManagerMock = mockStatic(DBManager.class)) {
           
            dbManagerMock.when(DBManager::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            repository.deleteBookGenres(1);

            verify(mockPreparedStatement).setInt(1, 1);
            verify(mockPreparedStatement).executeUpdate();
        }
    }
}