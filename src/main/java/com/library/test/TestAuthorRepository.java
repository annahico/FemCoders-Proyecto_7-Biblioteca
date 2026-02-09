package com.library.test;

import java.util.List;

import com.library.model.Author;
import com.library.model.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.AuthorRepositoryImpl;

public class TestAuthorRepository {

    public static void main(String[] args) {

        AuthorRepository authorRepo = new AuthorRepositoryImpl();

        System.out.println("=".repeat(60));
        System.out.println("TEST 1: Buscar autor por ID");
        System.out.println("=".repeat(60));

        Author author = authorRepo.getAuthorById(1);
        if (author != null) {
            System.out.println("Autor encontrado:");
            System.out.println("   ID: " + author.getId());
            System.out.println("   Nombre: " + author.getFullName());
        } else {
            System.out.println(" No se encontró el autor con ID 1");
        }

        System.out.println("" + "=".repeat(60));
        System.out.println("TEST 2: Buscar autor por nombre");
        System.out.println("=".repeat(60));

        Author author2 = authorRepo.getAuthorByName("Kafka");
        if (author2 != null) {
            System.out.println("Autor encontrado:");
            System.out.println("   ID: " + author2.getId());
            System.out.println("   Nombre: " + author2.getFullName());
        } else {
            System.out.println("No se encontró ningún autor con 'Kafka' en el nombre");
        }

        System.out.println("" + "=".repeat(60));
        System.out.println("TEST 3: Obtener libros de un autor");
        System.out.println("=".repeat(60));

        if (author2 != null) {
            List<Book> books = authorRepo.getBooksbyAuthor(author2);
            System.out.println("Libros de " + author2.getFullName() + ":");

            if (books.isEmpty()) {
                System.out.println("  No se encontraron libros");
            } else {
                for (Book book : books) {
                    System.out.println(" " + book.getTitle());
                    System.out.println("      ISBN: " + book.getIsbn());
                    System.out.println("      Géneros: " + book.getGenres());
                    System.out.println();
                }
            }
        }

        System.out.println("=".repeat(60));
        System.out.println("TEST 4: Crear un nuevo autor");
        System.out.println("=".repeat(60));

        Author newAuthor = Author.builder()
                .fullName("Gabriel García Márquez")
                .build();

        try {
            authorRepo.createAuthor(newAuthor);
            System.out.println("Autor creado con éxito:");
            System.out.println("   ID generado: " + newAuthor.getId());
            System.out.println("   Nombre: " + newAuthor.getFullName());
        } catch (Exception e) {
            System.out.println("Error al crear autor: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST 5: Actualizar un autor");
        System.out.println("=".repeat(60));

        if (newAuthor.getId() > 0) {
            newAuthor.setFullName("Gabriel José de la Concordia García Márquez");

            try {
                authorRepo.updateAuthor(newAuthor);
                System.out.println(" Autor actualizado");

                // Verificar que se actualizó
                Author updatedAuthor = authorRepo.getAuthorById(newAuthor.getId());
                if (updatedAuthor != null) {
                    System.out.println("   Nuevo nombre: " + updatedAuthor.getFullName());
                }
            } catch (Exception e) {
                System.out.println("Error al actualizar: " + e.getMessage());
            }
        }

        System.out.println("" + "=".repeat(60));
        System.out.println("TEST 6: Eliminar un autor");
        System.out.println("=".repeat(60));

        if (newAuthor.getId() > 0) {
            try {
                authorRepo.deleteAuthor(newAuthor.getId());
                System.out.println("Autor eliminado con ID: " + newAuthor.getId());

                // Verificar que se eliminó
                Author deletedAuthor = authorRepo.getAuthorById(newAuthor.getId());
                if (deletedAuthor == null) {
                    System.out.println("  Confirmado: el autor ya no existe en la BD");
                } else {
                    System.out.println("  Error: el autor todavía existe");
                }
            } catch (Exception e) {
                System.out.println("Error al eliminar: " + e.getMessage());
            }
        }

        System.out.println("" + "=".repeat(60));
        System.out.println("TESTS COMPLETADOS");
        System.out.println("=".repeat(60));
    }
}
