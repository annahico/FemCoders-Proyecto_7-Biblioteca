package com.library.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

}