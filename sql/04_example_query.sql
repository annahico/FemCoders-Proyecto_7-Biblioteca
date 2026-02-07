-- ================================================
-- QUERY 1: LISTAR TODOS LOS LIBROS
-- Muestra: id, título, ISBN, autor, géneros
-- Excluye: description
-- ================================================
SELECT
    b.id,
    b.title,
    b.isbn,
    a.full_name AS author,
    STRING_AGG(g.name, ', ') AS genres
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
GROUP BY b.id, b.title, b.isbn, a.full_name
ORDER BY b.title;


-- ================================================
-- QUERY 2: BUSCAR POR TÍTULO (muestra todos los campos)
-- Búsqueda flexible: encuentra si el título CONTIENE la palabra
-- Ejemplo: buscar "cuervos" encuentra "Seis de Cuervos"
-- ================================================
SELECT
    b.id,
    b.title,
    b.isbn,
    b.description,
    a.full_name AS author,
    STRING_AGG(DISTINCT g.name, ', ') AS genres
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
WHERE b.title ILIKE '%cuervos%'  -- Reemplazar 'cuervos' con el parámetro de búsqueda
GROUP BY b.id, b.title, b.isbn, b.description, a.full_name
ORDER BY b.title;


-- ================================================
-- QUERY 3: BUSCAR POR AUTOR (muestra todos los campos)
-- Búsqueda flexible: busca en el nombre completo del autor
-- Ejemplo: buscar "leigh" encuentra libros de "Leigh Bardugo"
-- ================================================
SELECT
    b.id,
    b.title,
    b.isbn,
    b.description,
    a.full_name AS author,
    STRING_AGG(DISTINCT g.name, ', ') AS genres
FROM books b
JOIN books_authors ba ON b.id = ba.book_id
JOIN authors a ON a.id = ba.author_id
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
WHERE a.full_name ILIKE '%leigh%'  -- Reemplazar 'leigh' con el parámetro de búsqueda
GROUP BY b.id, b.title, b.isbn, b.description, a.full_name
ORDER BY b.title;


-- ================================================
-- QUERY 4: BUSCAR POR GÉNERO (excluye description)
-- Búsqueda flexible: encuentra si el género CONTIENE la palabra
-- Ejemplo: buscar "fantasy" encuentra "Epic Fantasy", "Fantasy Literature", etc.
-- ================================================
SELECT DISTINCT
    b.id,
    b.title,
    b.isbn,
    a.full_name AS author,
    STRING_AGG(g.name, ', ') AS genres
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
        WHERE name ILIKE '%fantasy%'  -- Reemplazar 'fantasy' con el parámetro de búsqueda
    )
)
GROUP BY b.id, b.title, b.isbn, a.full_name
ORDER BY b.title;


-- ================================================
-- QUERY 5: OBTENER UN LIBRO POR ID
-- Muestra todos los campos de un libro específico
-- ================================================
SELECT
    b.id,
    b.title,
    b.isbn,
    b.description,
    b.created_at,
    b.updated_at,
    a.full_name AS author,
    STRING_AGG(DISTINCT g.name, ', ') AS genres
FROM books b
LEFT JOIN books_authors ba ON b.id = ba.book_id
LEFT JOIN authors a ON a.id = ba.author_id
LEFT JOIN books_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON g.id = bg.genre_id
WHERE b.id = 1  -- Reemplazar 1 con el parámetro de búsqueda
GROUP BY b.id, b.title, b.isbn, b.description, b.created_at, b.updated_at, a.full_name;


-- ================================================
-- QUERY 6: AÑADIR UN NUEVO LIBRO (PASO 1 - Insertar libro)
-- ================================================
INSERT INTO books (title, isbn, description)
VALUES ('New Book', '978-0-000-99999-9', 'Book description')
RETURNING id;  -- Devuelve el ID del libro recién creado


-- ================================================
-- QUERY 7: AÑADIR UN NUEVO AUTOR (si no existe)
-- ================================================
-- Primero verificar si el autor ya existe:
SELECT id FROM authors WHERE full_name = 'Full Name';

-- Si NO existe, insertarlo:
INSERT INTO authors (full_name)
VALUES ('Full Name')
RETURNING id;


-- ================================================
-- QUERY 8: CONECTAR LIBRO CON AUTOR
-- Después de insertar el libro y tener/crear el autor
-- ================================================
INSERT INTO books_authors (book_id, author_id)
VALUES (13, 1);  -- book_id = ID del nuevo libro, author_id = ID del autor


-- ================================================
-- QUERY 9: AÑADIR GÉNERO (si no existe)
-- ================================================
-- Primero verificar si el género ya existe:
SELECT id FROM genres WHERE name = 'Genre Name';

-- Si NO existe, insertarlo:
INSERT INTO genres (name)
VALUES ('Genre Name')
RETURNING id;


-- ================================================
-- QUERY 10: CONECTAR LIBRO CON GÉNERO
-- Puede conectar un libro con múltiples géneros
-- ================================================
INSERT INTO books_genres (book_id, genre_id)
VALUES (13, 1);  -- book_id = ID del libro, genre_id = ID del género


-- ================================================
-- QUERY 11: ACTUALIZAR UN LIBRO 
-- ================================================
UPDATE books
SET
    title = 'Updated Title',
    isbn = '978-0-000-88888-8',
    description = 'New description',
    updated_at = CURRENT_TIMESTAMP
WHERE id = 1;


-- ================================================
-- QUERY 12: ELIMINAR CONEXIONES DE UN LIBRO (antes de eliminar o actualizar autor/género)
-- ================================================
-- Eliminar todos los autores de un libro:
DELETE FROM books_authors WHERE book_id = 1;

-- Eliminar todos los géneros de un libro:
DELETE FROM books_genres WHERE book_id = 1;


-- ================================================
-- QUERY 13: ELIMINAR UN LIBRO
-- (CASCADE eliminará automáticamente las conexiones en books_authors y books_genres)
-- ================================================
DELETE FROM books WHERE id = 1;


-- ================================================
-- QUERY 14: CONTAR LIBROS TOTALES
-- ================================================
SELECT COUNT(*) AS total_books FROM books;


-- ================================================
-- QUERY 15: LISTAR TODOS LOS AUTORES
-- ================================================
SELECT id, full_name FROM authors ORDER BY full_name;


-- ================================================
-- QUERY 16: LISTAR TODOS LOS GÉNEROS
-- ================================================
SELECT id, name FROM genres ORDER BY name;


-- ================================================
-- QUERY 17: VERIFICAR SI UN ISBN YA EXISTE
-- (Útil antes de insertar un nuevo libro)
-- ================================================
SELECT EXISTS(SELECT 1 FROM books WHERE isbn = '978-0-451-65790-5') AS isbn_exists;


-- ================================================
-- QUERY 18: OBTENER ESTADÍSTICAS
-- ================================================
SELECT
    (SELECT COUNT(*) FROM books) AS total_books,
    (SELECT COUNT(*) FROM authors) AS total_authors,
    (SELECT COUNT(*) FROM genres) AS total_genres;


-- ================================================
-- NOTAS PARA INGRID (Repository):
--
-- 1. ILIKE vs LIKE:
--    - ILIKE = case-insensitive (no importa mayúsculas/minúsculas)
--    - LIKE = case-sensitive
--    Usar ILIKE para búsquedas más flexibles
--
-- 2. El símbolo % en las búsquedas:
--    - '%fantasy%' = encuentra "fantasy" en cualquier parte
--    - 'fantasy%' = encuentra "fantasy" al inicio
--    - '%fantasy' = encuentra "fantasy" al final
--
-- 3. PreparedStatement en Java:
--    Reemplazar los valores fijos con ? y usar setString(), setInt(), etc.
--    Ejemplo: WHERE b.title ILIKE ?
--    Y en Java: pstmt.setString(1, "%" + searchTerm + "%");
--
-- 4. RETURNING id:
--    Después de un INSERT, devuelve el ID generado automáticamente
--    Muy útil para obtener el ID del libro recién creado
--
-- 5. STRING_AGG:
--    Junta múltiples valores en uno solo separados por comas
--    Perfecto para mostrar todos los géneros de un libro
--
-- 6. CASCADE:
--    Al eliminar un libro, automáticamente elimina sus conexiones
--    en books_authors y books_genres (definido en CREATE TABLE)
-- ================================================