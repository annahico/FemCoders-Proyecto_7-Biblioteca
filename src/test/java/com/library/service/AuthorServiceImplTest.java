package com.library.service;

import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import com.library.model.Author;
import com.library.repository.AuthorRepository;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    private AuthorRepository mockRepository;

    private AuthorServiceImpl service;

    @BeforeEach

    void configurar() {
        service = new AuthorServiceImpl(mockRepository);
    }

    // TEST 1: Crear autor nuevo
    @Test
    void testCrearAutorNuevo() {
        // Simular que NO existe
        when(mockRepository.getAuthorByNameStrict("Nuevo Autor")).thenReturn(null);

        Author autorCreado = Author.builder()
                .id(10)
                .fullName("Nuevo Autor")
                .build();

        when(mockRepository.createAuthor(any(Author.class))).thenReturn(autorCreado);

        // Ejecutar
        Author resultado = service.createAuthorIfNotExists("Nuevo Autor");

        // Verificar
        assertNotNull(resultado);
        assertEquals(10, resultado.getId());
        assertEquals("Nuevo Autor", resultado.getFullName());
        verify(mockRepository).createAuthor(any(Author.class));
    }

    // TEST 2: Retornar autor existente
    @Test
    void testRetornarAutorExistente() {
        // Simular que SÍ existe
        Author autorExistente = Author.builder()
                .id(5)
                .fullName("Franz Kafka")
                .build();

        when(mockRepository.getAuthorByNameStrict("Franz Kafka")).thenReturn(autorExistente);

        // Ejecutar
        Author resultado = service.createAuthorIfNotExists("Franz Kafka");

        // Verificar
        assertNotNull(resultado);
        assertEquals(5, resultado.getId());
        assertEquals("Franz Kafka", resultado.getFullName());
        verify(mockRepository, never()).createAuthor(any(Author.class));
    }

    // TEST 3: Lanzar excepción si nombre vacío
    @Test
    void testNombreVacio() {
        // Ejecutar y verificar
        assertThrows(IllegalArgumentException.class, () -> {
            service.createAuthorIfNotExists("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.createAuthorIfNotExists("   ");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.createAuthorIfNotExists(null);
        });
    }

    // TEST 4: Buscar por ID
    @Test
    void testBuscarPorId() {
        Author autor = Author.builder()
                .id(1)
                .fullName("Paulo Coelho")
                .build();

        when(mockRepository.getAuthorById(1)).thenReturn(autor);

        // Ejecutar
        Author resultado = service.findById(1);

        // Verificar
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Paulo Coelho", resultado.getFullName());
    }

    // TEST 5: Buscar por nombre
    @Test
    void testBuscarPorNombre() {
        List<Author> autores = Arrays.asList(
                Author.builder().id(1).fullName("Franz Kafka").build(),
                Author.builder().id(2).fullName("Kafka Test").build()
        );

        when(mockRepository.getAuthorByName("Kafka")).thenReturn(autores);

        // Ejecutar
        List<Author> resultado = service.findByName("Kafka");

        // Verificar
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Franz Kafka", resultado.get(0).getFullName());
    }
}
