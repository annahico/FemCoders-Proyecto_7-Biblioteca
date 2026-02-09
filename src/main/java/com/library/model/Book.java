package com.library.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Versión corregida del modelo Book para que funcione correctamente con Lombok
 * Builder
 *
 * SOLUCIÓN AL PROBLEMA: - Removí @AllArgsConstructor porque interfiere con
 * @Builder.Default - Ahora @Builder.Default funcionará correctamente
 */
@Data
@Builder
public class Book {

    private int id;
    private String title;
    private String isbn;
    private String description;

    @Builder.Default
    private List<Author> authors = new ArrayList<>();

    @Builder.Default
    private List<Genre> genres = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor sin argumentos requerido por algunos frameworks
    public Book() {
        this.authors = new ArrayList<>();
        this.genres = new ArrayList<>();
    }

    // Constructor completo generado por Lombok Builder
    public Book(int id, String title, String isbn, String description,
            List<Author> authors, List<Genre> genres,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.authors = authors != null ? authors : new ArrayList<>();
        this.genres = genres != null ? genres : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
